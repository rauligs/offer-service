package uk.service.offer.persistence;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class InstantAttributeConverterTest {

    private InstantAttributeConverter dateConverter = new InstantAttributeConverter();

    @Test
    public void convertToDatabaseColumn_whenLocalDate_IsNotNull() {
        Timestamp timestamp = dateConverter.convertToDatabaseColumn(Instant.parse("2007-12-03T10:15:30.00Z"));
        Timestamp expectedTimestamp = Timestamp.valueOf("2007-12-03 10:15:30");
        assertThat(timestamp.compareTo(expectedTimestamp), is(0));
    }

    @Test
    public void convertToDatabaseColumn_whenLocalDate_IsNull() {
        assertThat(dateConverter.convertToDatabaseColumn(null), is(nullValue()));
    }

    @Test
    public void convertToEntityAttribute_whenDate_IsNotNull() {
        Timestamp timestamp = Timestamp.valueOf("2007-12-03 10:15:30");
        Instant expectedInstant = Instant.parse("2007-12-03T10:15:30.00Z");
        assertThat(dateConverter.convertToEntityAttribute(timestamp), is(expectedInstant));
    }

    @Test
    public void convertToEntityAttribute_whenDate_IsNull() {
        assertThat(dateConverter.convertToDatabaseColumn(null), is(nullValue()));
    }
}