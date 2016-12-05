package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class ListModelValueTest {

    @Test
    public void testListModelValue() {
        final ListModelValue value = new ListModelValue();
        assertNotNull(value);
        assertEquals(ModelType.LIST, value.getType());
    }

    @Test
    public void testListModelValueList() {
        final List<ModelNode> list = new ArrayList<ModelNode>();
        final ListModelValue value = new ListModelValue(list);
        assertNotNull(value);
        assertEquals(ModelType.LIST, value.getType());
    }

    @Test
    public void testListModelValueDataInput() {
        ModelNode list = ModelNode.fromString("[001,010,100]");
        Assert.assertEquals(3, list.asList().size());
        Assert.assertEquals("001", list.asList().get(0).asString());
        Assert.assertEquals("010", list.asList().get(1).asString());
        Assert.assertEquals("100", list.asList().get(2).asString());
    }

    @Test
    public void testWriteExternal() {
        // TODO implement test
    }

    @Test
    public void testFormatAsJSON() {
        final List<ModelNode> list = new ArrayList<ModelNode>();
        list.add(new ModelNode(new IntModelValue(5)));
        list.add(new ModelNode(new IntModelValue(6)));
        list.add(new ModelNode(new IntModelValue(7)));
        final ListModelValue value = new ListModelValue(list);

        final StringWriter stringWriter1 = new StringWriter();
        final PrintWriter writer1 = new PrintWriter(stringWriter1, true);
        value.formatAsJSON(writer1, 0, false);
        assertEquals("[5,6,7]", stringWriter1.toString());

        final StringWriter stringWriter2 = new StringWriter();
        final PrintWriter writer2 = new PrintWriter(stringWriter2, true);
        value.formatAsJSON(writer2, 0, true);
        assertEquals("[\n    5,\n    6,\n    7\n]", stringWriter2.toString());
    }

    @Test
    public void testCloneNotProtected() {
        final ModelNode model = new ModelNode(new ListModelValue());
        model.protect();
        try {
            model.add();
            fail("Should not allow child additions once protected");
        } catch(Exception expected) {
        }

        final ModelNode cloned = model.clone();
        cloned.add(); // Should not fail
    }
}
