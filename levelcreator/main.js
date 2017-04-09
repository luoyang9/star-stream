var level = {
	waves: []
}

var wave = {
	enemies: []
}

var enemyProperties = {
	UFO: ["x", "y", "dx", "dy", "stop", "delay"],
	Icarus: ["x", "y", "dx", "dy", "stop", "delay"],
	Striker: ["x", "y", "dx", "dy", "stop", "delay"],
	Valkyrie: ["x", "y", "dx", "dy", "stop", "delay"],
	Asteroid: ["x", "y", "dx", "dy", "delay"],
	Falcon: ["x", "y", "dx", "dy", "bound", "delay"],
	Kamikaze: ["x", "y", "dx", "dy", "stop", "delay"],
	Skullinator: ["x", "y", "dx", "dy", "stop", "delay"],
}

var propertyHTML = {
	x: '<input placeholder="x (0-480)" class="form-control" type="text" id="x" /><br/>',
	y: '<input placeholder="y (0-800)" class="form-control" type="text" id="y" /><br/>',
	dx: '<input placeholder="dx" class="form-control" type="text" id="dx" /><br/>',
	dy: '<input placeholder="dy" class="form-control" type="text" id="dy" /><br/>',
	stop: '<input placeholder="stop" class="form-control" type="text" id="stop" /><br/>',
	delay:  '<input placeholder="delay (in ms)" class="form-control" type="text" id="delay" /><br/>',
	bound: '<input placeholder="bound" class="form-control" type="text" id="bound" /><br/>'
}

$(document).ready(function() {

	for(var enemy in enemyProperties) {
		if(enemyProperties.hasOwnProperty(enemy)) {
			$("#enemy").append($('<option>', {
			    value: enemy,
			    text: enemy
			}));
		}
	}

	$("#enemy").on('change', function() {
		var html = "";
		var properties = enemyProperties[$("#enemy").val()];
		for(var i = 0; i < properties.length; i++) {
			html += propertyHTML[properties[i]];
		}
		$("#properties").html(html);
	})

	$("#enemy").change();

});

function createWave()
{
	level.waves.push(wave)
	wave = { enemies: [] }
	document.getElementById("level").innerHTML = JSON.stringify(level);
	document.getElementById("wave").innerHTML = JSON.stringify(wave);
}

function createEnemy()
{
	var enemy = {
		class: "xyz.charliezhang.shooter.entity.enemies." + $("#enemy").val()
	}

	var properties = enemyProperties[$("#enemy").val()];
	for(var i =0; i < properties.length; i++) {
		enemy[properties[i]] = parseInt($("#" + properties[i]).val())
	}

	wave.enemies.push(enemy);
	document.getElementById("wave").innerHTML = JSON.stringify(wave);
}