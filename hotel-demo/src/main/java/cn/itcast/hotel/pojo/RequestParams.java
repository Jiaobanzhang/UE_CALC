package cn.itcast.hotel.pojo;

import lombok.Data;

@Data
public class RequestParams {
    // 搜索和分页
    private String key;
    private Integer page;
    private Integer size;
    private String sortBy;
    // 过滤条件
    private String brand;
    private String city;
    private String starName;
    private Integer minPrice;
    private Integer maxPrice;
    private String location;
}
