package com.cw.java_language;

// 接口
//java类是单继承的。
//java接口可以多继承。
public interface ITestInterface extends IFatherInterface, IMotherInterface {
    public static final int ME_IS_STATIC_MEMBER = 10;
    public int mSimpleMember = 1;  // 成员变量默认使用 public static final 修饰

    // 接口的方法默认使用 public abstract 修饰
    public abstract void onStartInterface(String url);
    void onDoingInterface(String url);
    void onEndInterface(String url);
}
