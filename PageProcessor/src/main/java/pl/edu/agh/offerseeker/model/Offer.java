package pl.edu.agh.offerseeker.model;

import java.util.UUID;

/**
 * Created by Krzysztof Balon on 2014-10-22.
 */
public class Offer {
    private final UUID id;
    private final String description;

    public Offer(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
    }

    /**
     * @return offer unique ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return offer description
     */
    public String getDescription() {
        return description;
    }
}
