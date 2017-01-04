package devices.ms


import grails.rest.*
import grails.converters.*

class DeviceController extends RestfulController {
    static responseFormats = ['json', 'xml']
    DeviceController() {
        super(Device)
    }
	
	def getByManufacturer(String manufacturer, Integer max){
		if(manufacturer) {
		        def query = Product.where { 
		            manufacturer ==~ ~/$manufacturer/
		        }
		        respond query.list(max: Math.min( max ?: 10, 100)) 
		    }
		    else {
		        respond([]) 
		    }
	}
}
