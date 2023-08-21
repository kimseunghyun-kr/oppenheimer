package com.stock.oppenheimer.DTO.Kospi;

import lombok.Data;

import java.util.List;

@Data
public class KospiDTO {
    private Response response;

    @Data
    public static class Response {
        private ResponseHeader header;
        private ResponseBody body;

        @Data
        public static class ResponseHeader {
            private String resultCode;
            private String resultMsg;
        }

        @Data
        public static class ResponseBody {
            private int numOfRows;
            private int pageNo;
            private int totalCount;
            private Items items;

            @Data
            public static class Items {
                private List<KospiDTOItem> item;
            }
        }
    }
}
