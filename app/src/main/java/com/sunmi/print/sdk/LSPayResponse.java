package com.sunmi.print.sdk;

public class LSPayResponse {

    private int status; //返回状态  200-处理成功，其他-处理有误，详见错误码
    private String msg;//返回信息
    private Data data;//返回数据 json

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LSPayResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        private String mch_order_no;//支付订单号
        private String pay_order_id;//商户订单号 返回商户传入的订单号
        private int amount;
        private int order_state;//订单状态
        private PayInfo pay_info;//支付数据
        private String trans_status;//交易状态

        public String getMch_order_no() {
            return mch_order_no;
        }

        public void setMch_order_no(String mch_order_no) {
            this.mch_order_no = mch_order_no;
        }

        public String getPay_order_id() {
            return pay_order_id;
        }

        public void setPay_order_id(String pay_order_id) {
            this.pay_order_id = pay_order_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getOrder_state() {
            return order_state;
        }

        public void setOrder_state(int order_state) {
            this.order_state = order_state;
        }

        public PayInfo getPay_info() {
            return pay_info;
        }

        public void setPay_info(PayInfo pay_info) {
            this.pay_info = pay_info;
        }

        public String getTrans_status() {
            return trans_status;
        }

        public void setTrans_status(String trans_status) {
            this.trans_status = trans_status;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "mch_order_no='" + mch_order_no + '\'' +
                    ", pay_order_id='" + pay_order_id + '\'' +
                    ", amount=" + amount +
                    ", order_state=" + order_state +
                    ", pay_info=" + pay_info +
                    ", trans_status='" + trans_status + '\'' +
                    '}';
        }

        public static class PayInfo {
            private String gh_id;
            private String path;
            private String scheme_code;

            public String getGh_id() {
                return gh_id;
            }

            public void setGh_id(String gh_id) {
                this.gh_id = gh_id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getScheme_code() {
                return scheme_code;
            }

            public void setScheme_code(String scheme_code) {
                this.scheme_code = scheme_code;
            }

            @Override
            public String toString() {
                return "PayInfo{" +
                        "gh_id='" + gh_id + '\'' +
                        ", path='" + path + '\'' +
                        ", scheme_code='" + scheme_code + '\'' +
                        '}';
            }
        }

    }


}
