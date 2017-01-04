package devices.ms


import grails.rest.*

@Resource(uri = '/devices', readOnly = false, formats = ['json', 'xml'])
class Device {
	String manufacturer
	String brand
	String product
	String model
	String device
	String sdk_number
	
	Date lastAccess
	
	boolean is_debuggable
	
	static constraints = {
		manufacturer blank:true
		brand blank:true
		product blank:true
		model blank:true
		device blank:true
		sdk_number blank:true
		is_debuggable blank:true
	}
	
}