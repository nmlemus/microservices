package devices.ms


import grails.rest.*
import grails.converters.*
import com.mongodb.client.FindIterable
import static com.mongodb.client.model.Filters.*
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['ROLE_ADMIN'])
class DeviceController extends RestfulController {
    static responseFormats = ['json', 'xml']
    DeviceController() {
        super(Device)
    }
	
	def getByManufacturer(){
		
		
		FindIterable findIterable = Device.find(eq("manufacturer", "Samsung"))
		findIterable.limit(10).each { Device product -> println "Product title $product.manufacturer"}
		
		respond Device.find(eq("manufacturer", "Samsung")), view: 'index'
	}
}
