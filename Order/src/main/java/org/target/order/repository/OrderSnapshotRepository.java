package org.target.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.target.order.entity.OrderSnapshotDocument;

import java.util.Optional;

public interface OrderSnapshotRepository extends MongoRepository<OrderSnapshotDocument, String> {

    Optional<OrderSnapshotDocument> findByOrderId(Long orderId);
}