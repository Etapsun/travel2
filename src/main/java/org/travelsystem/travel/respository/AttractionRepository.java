package org.travelsystem.travel.respository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.travelsystem.travel.entity.Attraction;

import java.math.BigDecimal;
import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long>
{
    List<Attraction> findByAttractionNameContainingIgnoreCase(String keywords);//关键字名字中模糊查询（忽略大小写）
    List<Attraction> findByAttractionRatingBetween(BigDecimal min, BigDecimal max);// 根据评分范围查询
    //List<attraction> findByAttraction_nameContaining(String keywords);
    // 自定义更新浏览量（使用@Modifying）
    @Modifying//告诉 Spring 这是一个修改数据的操作（增删改），不是查询
    @Query("UPDATE Attraction a SET a.pageViews = a.pageViews + 1 WHERE a.attractionId = :attractionId")//作用：直接编写要执行的 SQL（实际是 JPQL，但语法类似）。
    void incrementPageViews(@Param("attractionId") Long attractionId);
    /**
     * @Param("attractionId") 将方法参数 attractionId 映射到 JPQL 中的 :attractionId。
     * 为什么返回 void：
     * UPDATE 操作不需要返回数据，只需确认执行成功（失败会抛异常）。
     */

}
