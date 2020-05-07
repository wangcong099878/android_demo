package the.one.gank.net;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

/**
 * @author The one
 * @date 2020/4/23 0023
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ResponseBuilder {

    private static Builder mBuilder;

    public static Builder getBuilder() {
        if(null == mBuilder){
            mBuilder = new Builder();
        }
        return mBuilder ;
    }

    public static class Builder{

        private  String eventStr ;
        private  String dataStr ;
        private  String msgStr ;
        private  String pageStr;

        private int successCode ;

        public String getEventStr() {
            return eventStr;
        }

        public Builder setEventStr(String eventStr) {
            this.eventStr = eventStr;
            return this;
        }

        public String getDataStr() {
            return dataStr;
        }

        public Builder setDataStr(String dataStr) {
            this.dataStr = dataStr;
            return this;
        }

        public String getMsgStr() {
            return msgStr;
        }

        public Builder setMsgStr(String msgStr) {
            this.msgStr = msgStr;
            return this;
        }

        public String getPageStr() {
            return pageStr;
        }

        public Builder setPageStr(String pageStr) {
            this.pageStr = pageStr;
            return this;
        }

        public int getSuccessCode() {
            return successCode;
        }

        public Builder setSuccessCode(int successCode) {
            this.successCode = successCode;
            return this;
        }
    }

}
