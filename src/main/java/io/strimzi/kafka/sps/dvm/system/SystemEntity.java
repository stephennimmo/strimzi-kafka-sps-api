package io.strimzi.kafka.sps.dvm.system;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

@Entity(name = "System")
@Table(name = "system")
public class SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_id")
    private Integer systemId;

    @Column(name = "name", unique = true)
    @NotEmpty
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemEntity that = (SystemEntity) o;
        return Objects.equals(systemId, that.systemId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemId, name);
    }

    @Override
    public String toString() {
        return "SystemEntity[" +
                "systemId=" + systemId +
                ", name='" + name + '\'' +
                ']';
    }

}