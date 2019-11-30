import random

dadosIniciais = 'listaEventos.add(new Evento(1, "Clone de chopp",                    1, "Rua 3", new GregorianCalendar(ano, mes, dia, 13, 30, 00)));\
        listaEventos.add(new Evento(2, "MARATONA DE CRUZES",                2, "Rua 5", new GregorianCalendar(ano, mes, dia, 12, 20, 00)));\
        listaEventos.add(new Evento(3, "Simpósio de Engenharia Mecânica",   3, "Rua 6", new GregorianCalendar(ano, mes, dia, 10, 10, 00)));\
        listaEventos.add(new Evento(4, "MARCELO FALCÃO NA VIBE",            2, "Rua 1", new GregorianCalendar(ano, mes, dia, 9,  50, 00)));\
        listaEventos.add(new Evento(5, "PEGADO - HOUSE CLUB",               4, "Rua 2", new GregorianCalendar(ano, mes, dia, 15, 10, 00)));\
        listaEventos.add(new Evento(6, "emPride",                           5, "Rua 9", new GregorianCalendar(ano, mes, dia, 6,  30, 00)));\
        listaEventos.add(new Evento(7, "Simpósio FBJ 2019",                 1, "Rua 4", new GregorianCalendar(ano, mes, dia, 20, 20, 00)));\
        listaEventos.add(new Evento(8, "10 anos da Matemática UFPE/CAA",    3, "Rua 7", new GregorianCalendar(ano, mes, dia, 21, 40, 00)));\
        listaEventos.add(new Evento(9, "4• Encontro de Tatuadores BJ-PE",   0, "Rua 8", new GregorianCalendar(ano, mes, dia, 14, 10, 00)));\
        listaEventos.add(new Evento(10, "Feira Afrocriativa 2019",          2, "Rua a", new GregorianCalendar(ano, mes, dia, 11, 00, 00)));\
        listaEventos.add(new Evento(11, "Carreiras Policias MDM",           1, "Rua b", new GregorianCalendar(ano, mes, dia, 18, 10, 00)));\
        listaEventos.add(new Evento(12, "3a Corrida Vamos Abraçar o Sol",   2, "Rua c", new GregorianCalendar(ano, mes, dia, 17, 20, 00)));\
        listaEventos.add(new Evento(13, "34ª Corrida da Consciência Negra", 4, "Rua d", new GregorianCalendar(ano, mes, dia, 8,  30, 00)));'
    
linhas = dadosIniciais.split(';')

lat1 = 88791135
lat2 = 88931104
long1 = 364684465
long2 = 364988789

for l in linhas[:-1]:
    inicio,fim = l.split('"Rua ')
    print(inicio, '-'+str(random.randint(lat1, lat2)/10000000)+', -'+str(random.randint(long1, long2)/10000000)+', "Rua',fim+';')