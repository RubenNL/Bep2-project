//select travel_class.id AS travel_class_id,flight.id AS flight_id from flight INNER JOIN plane ON flight.plane_code=plane.code INNER JOIN travel_class ON travel_class.plane_type_id=plane.type_id;
data=require('fs').readFileSync('travel_class_flight.raw','utf8').split('\n').map((line,id)=>{
	parts=line.split('\t')
	return '('+id+','+parts[0]+','+parts[1]+')'
}).join(',')
console.log('INSERT INTO travel_class_flight(data,id,travel_class_id) VALUES '+data+';')
