package org.travelsystem.travel.respository;
/**
 * Created by IntelliJ IDEA.
 * User: Lenovo
 * Date: 2021/4/27
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.travelsystem.travel.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}