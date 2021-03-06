package devices.ms

class UrlMappings {

    static mappings = {
		
		"/api/devices"(resources:"device")
		
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
		
		"/devices"(resources:"device")
		
		"/byManufacturer"(controller: 'device', action: 'getByManufacturer')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
