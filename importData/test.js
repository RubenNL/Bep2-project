const csv = require('csv-parser')
const fs = require('fs')
function planes() {
	const results = [];
	fs.createReadStream('planes.csv','utf16LE')
	.pipe(csv())
	.on('data', (data) => {if((data.Manufacturer.includes('Boeing') || data.Manufacturer.includes('Airbus')) && data.Group=="Large aeroplane") results.push(data)})
	.on('end', () => {
		var modelItems=[...new Set(results.map(line=>line.Model.split("'").join('')))]
		var models='INSERT INTO planetype (id, name) VALUES '+modelItems.map((type,id)=>'('+id+", '"+type+"')")+';';
		var planes='INSERT INTO plane (code, type_id) VALUES '+results.map(plane=>"('"+Object.values(plane)[0]+"',"+modelItems.indexOf(plane.Model.split("'").join(""))+')')+';';
		fs.writeFileSync('planetype+plane.sql',models+'\n'+planes,'utf8');
	});
}
function airports() {
	const results=[]
	fs.createReadStream('airports.csv','utf8')
	.pipe(csv())
	.on('data', (data) => {if(data.type=='large_airport'&&data.iata_code!='') results.push(data)})
	.on('end', () => {
		var airports='INSERT INTO airport(code,country,lat,lng,name,place) VALUES '+results.map(airport=>"('"+airport.iata_code+"', '"+airport.iso_country+"', "+airport.latitude_deg+", "+airport.longitude_deg+", '"+airport.name.split("'").join('')+"', '"+airport.municipality.split("'").join('')+"')")+';';
		fs.writeFileSync('airports.sql',airports,'utf8');
	});
}
airports();
planes();
