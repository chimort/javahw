package payments.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "outbox_events")
public class OutboxEvent_ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregate_type", nullable = false)
    private String aggregateType;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "payload", columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(name = "sent", nullable = false)
    private boolean sent = false;

    public OutboxEvent_() {
    }

    public OutboxEvent_(String eventType, String payload) {
        this.eventType = eventType;
        this.payload = payload;
        this.sent = false;
    }

    public Long getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }


    public String getPayload() {
        return payload;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
