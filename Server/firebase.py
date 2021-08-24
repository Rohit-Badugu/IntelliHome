def firebasepush(data):
	print data
	db.post('/Log',data)


def firebaseget():
	data = db.get('/Log',None)
	data = data.values()
	return data


