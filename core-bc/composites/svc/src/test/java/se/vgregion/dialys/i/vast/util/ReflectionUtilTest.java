package se.vgregion.dialys.i.vast.util;

import org.junit.Test;
import se.vgregion.dialys.i.vast.jpa.User;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ReflectionUtilTest {

    @Test
    public void getDeclaredFieldSuperClass() throws NoSuchFieldException {
        Field tillDatum = ReflectionUtil.getDeclaredField("firstName", User.class);

        assertNotNull(tillDatum);
    }

    @Test
    public void getDeclaredFieldThisClass() throws NoSuchFieldException {
        Field tillDatum = ReflectionUtil.getDeclaredField("firstName", User.class);

        assertNotNull(tillDatum);
    }

    @Test
    public void getDeclaredFieldNotFound() throws NoSuchFieldException {
        Field tillDatum = ReflectionUtil.getDeclaredField("asdfasdfaea", User.class);

        assertNull(tillDatum);
    }
}