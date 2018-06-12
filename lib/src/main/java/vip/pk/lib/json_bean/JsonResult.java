package vip.pk.lib.json_bean;



public class JsonResult {


    /**
     * Code : 0
     * Msg : 消息
     * Data : 更多信息
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
