//
// Created by test on 19-11-5.
//

#ifndef BOOSTPROJ_MAIN_H
#define BOOSTPROJ_MAIN_H


class Pointer {
  public:
    // 可以修改指针指向地址的值,函数外部也改变
    void passPointer(int * p) {
        *p = 2;
    }

    // 可以修改指针指向另一个地址,函数外部未改变
    void passPointer2(int * p) {
        p = (int*) new int[2];
        printf("fun addr: %p, value: %d \n", p, *p);
    }

    // 无修改指针指向地址的值
    void passConstPointer(const int* p) {
//        *p = 2;
    }

    // 可以修改指针指向另一个地址,函数外部未改变
    void passConstPointer2(const int* p) {
        p = (int*)new int[2];
        printf("fun addr: %p, value: %d \n", p, *p);
    }

    void passConstPointer3(const int* const p) {
//        p = (int*)new int[2];
    }
};

#endif //BOOSTPROJ_MAIN_H
