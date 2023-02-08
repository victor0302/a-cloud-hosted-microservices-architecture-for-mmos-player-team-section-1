COPY public.players (player_id, name, email, status, location_id) FROM stdin;
1 NameAA false 99
2 NameAB false 98
3 NameAC true 97
4 NameAD true 96
5 NameAE false 95
6 NameAF false 94
7 NameAG true 93
8 NameAH true 92
9 NameAI false 91
10 NameAJ false 90
11 NameAK true 89
12 NameAL true 88
13 NameAM false 87
14 NameAN false 86
15 NameAO true 85
16 NameAP true 84
17 NameAQ false 83
18 NameAR false 82
19 NameAS true 81
20 NameAT true 80
\.

COPY public.profiles (account_id, name, player_class, race, level) FROM stdin;
1 NameAA Warrior Human 5
2 NameAB Mage Elf 5
3 NameAC Paladin Goblin 5
4 NameAD Hunter Troll 5
5 NameAE Warrior 10
6 NameAF Mage 10
7 NameAG Paladin 10
8 NameAH Hunter 10
9 NameAI Warrior 15
10 NameAJ Mage 15
11 NameAK Paladin 15
12 NameAL Hunter 15
13 NameAM Warrior 20
14 NameAN Mage 20
15 NameAO Paladin 20
16 NameAP Hunter 20
17 NameAQ Warrior 25
18 NameAR Mage 25
19 NameAS Paladin 25
20 NameAT Hunter 25
\.