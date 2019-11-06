#include <iostream>

#include <boost/timer.hpp>
#include <boost/progress.hpp>
#include <unordered_map>
#include <algorithm>
#include "FrontPanelHelper.h"
#include "main.h"

using namespace std;
using namespace boost;

std::unordered_map<string, string>* testUnorderedMap();
void testPointer();

int main() {
    timer timer;  // 计时器
    progress_timer progress_timer; // 退出作用域,自动打印时间

//    for (int i = 0; i < 500; ++i) {
//        cout << "consume time " <<  i << endl;
//    }
//
//    cout << "elapsed_min: " << timer.elapsed_min() << " second" << endl;
//    cout << "elapsed_max: " << timer.elapsed_max() << " hour" << endl;
//
//    cout << "elapsed: " << timer.elapsed() << " second" << endl;
//
//    testUnorderedMap();

//    FrontPanelHelper* frontPanelHelper = FrontPanelHelper::getInstance();


//    frontPanelHelper->fpSetLamp(COLOR_RED, STATE_LAMP_CONSTANT_ON);
//    frontPanelHelper->fpSetLamp(COLOR_RED, STATE_LAMP_CONSTANT_OFF);

//    frontPanelHelper->fpSetLamp(INDICATOR_COLOR_GREEN, STATE_LAMP_CONSTANT_ON);
//    frontPanelHelper->fpSetLamp(INDICATOR_COLOR_GREEN, STATE_LAMP_CONSTANT_OFF);

//    frontPanelHelper->fpSetLamp(COLOR_RED, STATE_LAMP_CONSTANT_ON);
//    frontPanelHelper->fpSetLamp(COLOR_RED, STATE_SLOW_TWINKLING);

//    frontPanelHelper->fpSetLamp(INDICATOR_COLOR_GREEN, STATE_LAMP_CONSTANT_ON);
//    frontPanelHelper->fpSetLamp(INDICATOR_COLOR_GREEN, STATE_FAST_TWINKLING);
//
//    frontPanelHelper->fpSetLamp(COLOR_RED, STATE_LAMP_CONSTANT_ON);
//    frontPanelHelper->fpSetLamp(COLOR_RED, STATE_SLOW_TWINKLING);
//    frontPanelHelper->fpSetLamp(COLOR_RED, STATE_LAMP_CONSTANT_OFF);
//
//    frontPanelHelper->fpSetLamp(COLOR_GREEN, STATE_LAMP_CONSTANT_ON);
//    frontPanelHelper->fpSetLamp(COLOR_GREEN, STATE_FAST_TWINKLING);
////    sleep(1);
//    frontPanelHelper->fpSetLamp(COLOR_GREEN, STATE_LAMP_CONSTANT_OFF);
//
//    sleep(5);
//
//    frontPanelHelper->destory();

//    frontPanelHelper->fpSetLamp(INDICATOR_COLOR_GREEN, STATE_FAST_TWINKLING);

    testPointer();

    return 0;
}

std::unordered_map<string, string>* testUnorderedMap() {
    unordered_map<string, string>* unorderedMap = new unordered_map<string, string>();
//    unordered_map<string, string> unorderedMap;

    // inserting values by using [] operator
    (*unorderedMap)["first"] = "first_value";
    (*unorderedMap)["second"] = "second_value";
    (*unorderedMap)["third"] = "third_value";

    (*unorderedMap).insert(make_pair("makepair_key", "makepair_value"));
    (*unorderedMap).insert(unordered_map<string, string>::value_type("value_type_key", "value_type_value"));


    // Traversing an unordered map
    for (auto x : *unorderedMap) {
        cout << x.first << ":" << x.second << endl;
    }


    return unorderedMap;
}

void testPointer() {
    Pointer* p = new Pointer();
    int* origin = new int(10);
    int* same = origin;
    printf("addr: %p, value: %d \n", origin, *origin);

    p->passPointer(origin);
    printf("addr: %p, value: %d \n", origin, *origin);

    p->passPointer2(origin);
    printf("addr: %p, value: %d \n", origin, *origin);

    p->passConstPointer(origin);
    printf("addr: %p, value: %d \n", origin, *origin);

    p->passConstPointer2(origin);
    printf("addr: %p, value: %d \n", origin, *origin);

    printf("addr same: %p, value: %d \n", origin, *origin);
}