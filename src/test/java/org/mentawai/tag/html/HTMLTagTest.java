package org.mentawai.tag.html;

import org.junit.Test;

import junit.framework.Assert;

public class HTMLTagTest {

	@Test
	public void testGetExtraAttributes() {

		Input input = new Input();
		input.setExtra("readonly=readonly");
		Assert.assertEquals(" readonly=\"readonly\"", input.getExtraAttributes());
		
		input.setExtra("readonly=readonly#checked=checked");
		Assert.assertEquals(" readonly=\"readonly\" checked=\"checked\"", input.getExtraAttributes());
		
		input.setExtra("readonly=readonly#checked=checked#onclick=functionX('parx');");
		Assert.assertEquals(" readonly=\"readonly\" checked=\"checked\" onclick=\"functionX('parx');\"", input.getExtraAttributes());
		
		input.setExtra("readonly=readonly#checked=checked#onclick=functionX('parx','=123');");
		Assert.assertEquals(" readonly=\"readonly\" checked=\"checked\" onclick=\"functionX('parx','=123');\"", input.getExtraAttributes());
	}
	
	@Test
	public void testGetExtraAttributesCustomSeparator() {

		Input input = new Input();
		input.setSeparator("##");
		input.setExtra("readonly=readonly");
		Assert.assertEquals(" readonly=\"readonly\"", input.getExtraAttributes());
		
		input.setExtra("readonly=readonly##checked=checked");
		Assert.assertEquals(" readonly=\"readonly\" checked=\"checked\"", input.getExtraAttributes());
		
		input.setExtra("readonly=readonly##checked=checked##onclick=functionX('parx');");
		Assert.assertEquals(" readonly=\"readonly\" checked=\"checked\" onclick=\"functionX('parx');\"", input.getExtraAttributes());
		
		input.setExtra("readonly=readonly##checked=checked##onclick=functionX('parx','=123');");
		Assert.assertEquals(" readonly=\"readonly\" checked=\"checked\" onclick=\"functionX('parx','=123');\"", input.getExtraAttributes());
	}

}
