package vip.pk.lib.bean;

public class ResultBean {


    /**
     * Code : 1
     * Msg : 获取用户信息出错,请重新登录
     * Data : null
     */

    private int Code;
    private String Msg;
    private Object Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }
}
