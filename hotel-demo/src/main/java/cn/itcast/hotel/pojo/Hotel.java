/**
 * 这个类是和 sql 数据库中的表结构相同
 * 但是这个类和 es 索引库的定义还有点区别, 需要将 longitude 和 latitude 统一合并成 location
 * 所以重新建立了另外一个类 HotelDoc
 */
package cn.itcast.hotel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_hotel")
public class Hotel {
    @TableId(type = IdType.INPUT)
    private Long id;
    private String name;
    private String address;
    private Integer price;
    private Integer score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    private String longitude;
    private String latitude;
    private String pic;
}
