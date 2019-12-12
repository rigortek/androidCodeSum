package com.cw.java_language;

public class ImplementClass extends AbstractClass implements ITestInterface {

    public ImplementClass() {
        System.out.println(ME_IS_STATIC_MEMBER);
        // 成员变量默认使用 public static final 修饰,无法修改
//        mSimpleMember = 10;
        System.out.println(mSimpleMember);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDoing() {
        super.onDoing();
    }

    @Override
    public void onEnd() {
        super.onEnd();
    }

    @Override
    public void onStartInterface(String url) {

    }

    @Override
    public void onDoingInterface(String url) {

    }

    @Override
    public void onEndInterface(String url) {

    }

    @Override
    public void onFather() {

    }

    @Override
    public void onMother() {

    }
}