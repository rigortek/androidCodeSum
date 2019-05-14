
#pragma once 

#include <pthread.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>

class FrontPanelHelper;
void* start_routine(void * arg);

typedef enum {
    STATE_LAMP_CONSTANT_ON = 0,
    STATE_LAMP_CONSTANT_OFF = 1,
    STATE_SLOW_TWINKLING = 2,
    STATE_FAST_TWINKLING = 3,
} LampState;

enum {
  COLOR_RED = 0x000000FF,
  COLOR_GREEN = 0x0000FF00,
};

class LampRequest {
  public:
    LampRequest() {
        reset();
    }
    virtual ~LampRequest() {}

    void reset() {
        mColor = -1;
        mState = -1;
        mCancelled = false;
    }

    int isValid() {
        return -1 != mColor || -1 != mState;
    }

  public:
    int mColor;
    int mState;
    bool mCancelled;
};


void setLampImpl(int color, int state) {
    switch (color) {
        case COLOR_GREEN:
            switch (state) {
                case STATE_LAMP_CONSTANT_OFF:
                    printf("COLOR_GREEN : STATE_LAMP_CONSTANT_OFF \n");
                    break;
                case STATE_LAMP_CONSTANT_ON:
                    printf("COLOR_GREEN : STATE_LAMP_CONSTANT_ON \n");
                    break;
                case STATE_FAST_TWINKLING:
                    printf("COLOR_GREEN : STATE_FAST_TWINKLING \n");
                    break;
                case STATE_SLOW_TWINKLING:
                    printf("COLOR_GREEN : STATE_SLOW_TWINKLING \n");
                    break;
                default:
                    break;
            }
            break;
        case COLOR_RED:
            switch (state) {
                case STATE_LAMP_CONSTANT_OFF:
                    printf("COLOR_RED : STATE_LAMP_CONSTANT_OFF \n");
                    break;
                case STATE_LAMP_CONSTANT_ON:
                    printf("COLOR_RED : STATE_LAMP_CONSTANT_ON \n");
                    break;
                case STATE_FAST_TWINKLING:
                    printf("COLOR_RED : STATE_FAST_TWINKLING \n");
                    break;
                case STATE_SLOW_TWINKLING:
                    printf("COLOR_RED : STATE_SLOW_TWINKLING \n");
                    break;
                default:
                    break;
            }
            break;
        default:
            break;
    }
}

class FrontPanelHelper {
  public:
    const int SLOW_TWINKLING_INTERVAL = 1000 * 1000;  // 1s
    const int FAST_TWINKLING_INTERVAL = 200 * 1000;  // 0.1s

    static FrontPanelHelper* getInstance() {
        if (NULL != mspInstance) {
            return mspInstance;
        }

        pthread_mutex_lock(&mInstanceMutex);
        if (NULL == mspInstance) {
            mspInstance = new FrontPanelHelper();
        }
        pthread_mutex_unlock(&mInstanceMutex);

        return mspInstance;
    }
    
    static void destory() {
        pthread_mutex_lock(&mInstanceMutex);
        if (NULL != mspInstance) {
            delete mspInstance;
            mspInstance = NULL;
        }
        pthread_mutex_unlock(&mInstanceMutex);
    }

  private:
    FrontPanelHelper() {
        pthread_mutex_init(&mInstanceMutex, NULL);

        pthread_mutex_init(&mWaitMutex, NULL);
        pthread_mutex_init(&mRequestMutex, NULL);
        pthread_cond_init(&mCondition, NULL);

        startRun();
    }

    virtual ~FrontPanelHelper() {
    }

    friend void* start_routine(void * arg);

    void startRun() {
        if (mIsRun) {
            return;
        }
        mExited = false;
        mIsRun = true;

        int result = pthread_create(&mThreadId, NULL, start_routine, (void*) this);
        if (result != 0){
            printf("can't create thread: %s\n", strerror(result));
        }
    }

    void stopRun() {
        mIsRun = false;

        pthread_mutex_lock(&mWaitMutex);
        pthread_cond_signal(&mCondition);
        pthread_mutex_unlock(&mWaitMutex);

        while (!mExited) {
            usleep(200);
        }
    }

  public:
    int fpSetLamp(int color, int state) {
        switch (state) {
            case STATE_LAMP_CONSTANT_ON:
            case STATE_LAMP_CONSTANT_OFF: { // excute immediately
                pthread_mutex_lock(&mRequestMutex);
                if (mCurLampRequest.isValid()) {
                    mCurLampRequest.mCancelled = true;
                }
                pthread_mutex_unlock(&mRequestMutex);

                setLampImpl(color, state);
            }
                break;

            case STATE_SLOW_TWINKLING:
            case STATE_FAST_TWINKLING: {
                pthread_mutex_lock(&mRequestMutex);
                if (!mCurLampRequest.isValid()) {
                    mCurLampRequest.mColor = color;
                    mCurLampRequest.mState = state;
                    mCurLampRequest.mCancelled = false;
                } else {
                    mNewLampRequest.mColor = color;
                    mNewLampRequest.mState = state;
                    mNewLampRequest.mCancelled = false;

                    mCurLampRequest.mCancelled = true;
                }
                pthread_mutex_unlock(&mRequestMutex);

                pthread_mutex_lock(&mWaitMutex);
                pthread_cond_signal(&mCondition);
                pthread_mutex_unlock(&mWaitMutex);
            }
            break;

            default:
                break;
        }
    }

  private:
    void run() {
        while (mIsRun) {
            pthread_mutex_lock(&mWaitMutex);
            pthread_cond_wait(&mCondition, &mWaitMutex);
            pthread_mutex_unlock(&mWaitMutex);

            if (!mIsRun) {
                break;
            }

            if (mCurLampRequest.isValid()) {
                switch (mCurLampRequest.mState) {
                    case STATE_LAMP_CONSTANT_ON:
                    case STATE_LAMP_CONSTANT_OFF: {
                    }
                    break;

                    case STATE_SLOW_TWINKLING:
                    case STATE_FAST_TWINKLING: {
                        bool cancelled = false;
                        do {
                            pthread_mutex_lock(&mRequestMutex);
                            bool cancelled = mCurLampRequest.mCancelled;

                            if (cancelled) {  // start check
                                if (mNewLampRequest.isValid()) {
                                    mCurLampRequest = mNewLampRequest;
                                } else {
                                    mCurLampRequest.reset();
                                    break;
                                }
                            }
                            int color = mCurLampRequest.mColor;
                            int state = mCurLampRequest.mState;
                            pthread_mutex_unlock(&mRequestMutex);

                            setLampImpl(color, state);

                            usleep(STATE_SLOW_TWINKLING == state ? SLOW_TWINKLING_INTERVAL : FAST_TWINKLING_INTERVAL);

                            pthread_mutex_lock(&mRequestMutex);
                            cancelled = mCurLampRequest.mCancelled;
                            if (cancelled) {  // end check again
                                if (mNewLampRequest.isValid()) {
                                    mCurLampRequest = mNewLampRequest;
                                } else {
                                    mCurLampRequest.reset();
                                    break;
                                }
                            }
                            pthread_mutex_unlock(&mRequestMutex);
                        } while (true);
                    }
                    break;
                }
            }
  
            if (!mIsRun) {
                break;
            }
        }
        mExited = true;
    }

  private:
    static FrontPanelHelper* mspInstance;
    static pthread_mutex_t mInstanceMutex;

    pthread_mutex_t mWaitMutex;
    pthread_mutex_t mRequestMutex;

    LampRequest mCurLampRequest;
    LampRequest mNewLampRequest;

    pthread_cond_t mCondition;

    pthread_t mThreadId;

    volatile bool mIsRun;
    volatile bool mExited;
};

void* start_routine(void * arg) {
    FrontPanelHelper* p = (FrontPanelHelper*)(arg);
    p->run();
}


FrontPanelHelper* FrontPanelHelper::mspInstance = NULL;
pthread_mutex_t FrontPanelHelper::mInstanceMutex;