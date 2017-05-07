CREATE TABLE rec_table(_id integer primary key autoincrement, date integer default 0, id_vehicle long, note string default '',mileage integer default 0, volume float default 0, volumecost float default 0, cost float default 0,type string default '',mark integer default 0,mileageadd integer default 0,current_tank integer default 0,tankvolume float default 0,volumemileage float default 0,costmileage float default 0,volumemileage_1 float default 0,costmileage_1 float default 0,mil_coef float default 1,consumption_comp_0 float default 0,consumption_comp_1 float default 0,missed integer default 0);
INSERT INTO `rec_table` VALUES (2,20150721,1,'',148488,35.9199981689453,33.8499984741211,1215.89001464844,3,10,519,0,0.0,6.92100143432617,2.34275889396667,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (3,20150726,1,'',149000,35.3899993896484,33.8499984741211,1197.94995117188,3,10,512,0,0.0,6.912109375,2.33974885940552,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (4,20150730,1,'',149525,33.3300018310547,33.8499984741211,1128.21997070313,3,10,525,0,0.0,6.34857177734375,2.14899134635925,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (5,20150802,1,'',149961,34.939998626709,32.9000015258789,1149.53002929688,3,10,436,0,0.0,8.01376247406006,2.63652777671814,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (6,20150805,1,'',150417,29.2000007629395,33.8499984741211,988.419982910156,3,10,456,0,0.0,6.40350818634033,2.16758751869202,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (7,20150808,1,'',150852,28.6800003051758,33.8499984741211,970.820007324219,3,10,435,0,0.0,6.59310340881348,2.23176550865173,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (8,20150822,1,'',151224,28.7099990844727,33.689998626709,967.239990234375,3,10,372,0,0.0,7.71774196624756,2.60010695457458,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (9,20150826,1,'',151661,32.0499992370605,34.3899993896484,1102.19995117188,3,10,437,0,0.0,7.33409643173218,2.52219581604004,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (10,20150830,1,'',152114,32.3400001525879,33.9900016784668,1099.23999023438,3,10,453,0,0.0,7.13907194137573,2.42657089233398,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (11,20150901,1,'',152602,34.9599990844727,34.5900001525879,1209.27001953125,3,10,488,0,0.0,7.16393423080444,2.47800493240356,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (12,20150904,1,'',153062,31.3400001525879,34.3499984741211,1076.53002929688,3,10,460,0,0.0,6.81304264068604,2.34028005599976,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (13,20150911,1,'',153501,32.060001373291,34.2999992370605,1099.66003417969,3,10,439,0,0.0,7.30296087265015,2.50491523742676,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (14,20150915,1,'',153980,30.3400001525879,34.3499984741211,1042.18005371094,3,10,479,0,0.0,6.33402824401856,2.17573857307434,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (15,20150918,1,'',154432,33.1800003051758,34.3499984741211,1139.72998046875,3,10,452,0,0.0,7.34070777893066,2.52153301239014,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (16,20150924,1,'',154913,36.5999984741211,34.689998626709,1269.65002441406,3,10,481,0,0.0,7.60914707183838,2.63961315155029,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (17,20150928,1,'',155411,33.6100006103516,34.3499984741211,1154.5,3,10,498,0,0.0,6.74899578094482,2.31827998161316,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (18,20151001,1,'',155836,32.0400009155273,34.8899993896484,1117.88000488281,3,10,425,0,0.0,7.53882360458374,2.63029551506042,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (19,20151004,1,'',156302,33.2900009155273,34.689998626709,1154.82995605469,3,10,466,0,0.0,7.14377689361572,2.47817611694336,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (20,20151009,1,'',157125,32.0299987792969,34.3499984741211,1100.22998046875,3,10,823,0,0.0,3.89185905456543,1.33685350418091,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (21,20151010,1,'',157558,34.189998626709,34.9799995422363,1195.96997070313,3,10,433,0,0.0,7.89607429504395,2.76204681396484,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (22,20151016,1,'С карты',158052,30.1700000762939,34.3499984741211,1036.33996582031,3,10,494,0,0.0,6.10728693008423,2.09785294532776,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (23,20151019,1,'',158514,34.6699981689453,34.3499984741211,1190.91003417969,3,10,462,0,0.0,7.50432825088501,2.57773685455322,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (24,20151024,1,'',158947,32.689998626709,34.3499984741211,1122.90002441406,3,10,433,0,0.0,7.54965400695801,2.59330606460571,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (25,20151027,1,'',159327,30.4200000762939,34.3499984741211,1044.93005371094,3,10,380,0,0.0,8.00526237487793,2.74980759620666,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (26,20151030,1,'',159806,34.0200004577637,34.9799995422363,1190.02001953125,3,10,479,0,0.0,7.10229730606079,2.48438358306885,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (27,20151103,1,'',160270,36.2400016784668,34.689998626709,1257.17004394531,3,10,464,0,0.0,7.81034564971924,2.70940899848938,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (28,20151106,1,'',160731,31.1900005340576,34.3499984741211,1071.38000488281,3,10,461,0,0.0,6.76572704315186,2.32402729988098,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (29,20151108,1,'',161189,15.0,33.2999992370605,499.5,3,8,458,0,0.0,8.73362445831299,2.90829706192017,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (30,20151109,1,'',161309,29.4699993133545,34.8899993896484,1028.2099609375,3,10,120,0,0.0,3.72500109672546,1.29965281486511,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (31,20151113,1,'',161762,34.060001373291,34.8899993896484,1188.34997558594,3,10,453,0,0.0,7.51876306533814,2.62329649925232,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (32,20151116,1,'',162247,31.2600002288818,34.689998626709,1084.41003417969,3,10,485,0,0.0,6.44536113739014,2.23589563369751,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (33,20151118,1,'',162610,27.6100006103516,34.3499984741211,948.400024414062,3,10,363,0,0.0,7.60606050491333,2.61268186569214,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (34,20151121,1,'',162986,27.0599994659424,34.689998626709,938.710021972656,3,10,376,0,0.0,7.19680786132813,2.49657273292541,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (35,20151125,1,'',163385,33.0,34.3499984741211,1133.55004882813,3,10,399,0,0.0,8.270676612854,2.84097719192505,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (36,20151129,1,'',163822,31.4500007629395,34.3499984741211,1080.31005859375,3,10,437,0,0.0,7.19679594039917,2.47209930419922,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (37,20151206,1,'',164204,28.9400005340576,34.689998626709,1003.92999267578,3,10,382,0,0.0,7.57591676712036,2.62808537483215,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (38,20151209,1,'',164587,35.1300010681152,34.5900001525879,1215.15002441406,3,10,383,0,0.0,9.17232513427734,3.17270708084106,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (39,20151213,1,'',165024,29.6900005340576,33.5499992370605,996.099975585937,3,10,437,0,0.0,6.79405069351196,2.27940392494202,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (40,20151218,1,'',165418,30.7299995422363,33.3499984741211,1024.84997558594,3,10,394,0,0.0,7.7994909286499,2.60113024711609,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (41,20151226,1,'',165908,32.1500015258789,34.5900001525879,1112.06994628906,3,10,490,0,0.0,6.56122493743897,2.26952767372131,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (42,20151227,1,'',166260,25.8999996185303,34.3499984741211,889.659973144531,3,10,352,0,0.0,7.35795545578003,2.52745747566223,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (43,20151229,1,'',166617,29.5400009155273,33.3499984741211,985.159973144531,3,10,357,0,0.0,8.27450942993164,2.7595489025116,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (44,20160102,1,'',166958,15.0,32.9000015258789,493.5,3,8,341,0,0.0,11.7302055358887,3.85923743247986,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (47,20160107,1,'',167149,34.0999984741211,33.25,1133.81994628906,3,10,191,0,0.0,4.76439714431763,1.58416199684143,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (48,20160110,1,'',167467,27.8299999237061,34.5900001525879,962.640014648438,3,10,318,0,0.0,8.75157356262207,3.02716898918152,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (52,20160114,1,'',167723,28.8899993896484,33.8499984741211,977.929992675781,3,10,256,0,0.0,11.28515625,3.82002520561218,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (53,20160118,1,'',168123,32.1500015258789,33.2000007629395,1067.38000488281,3,10,400,0,0.0,8.03750038146973,2.66845035552979,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (54,20160123,1,'',168533,30.7700004577637,32.5,1000.0,3,8,410,0,0.0,9.7560977935791,3.17073178291321,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (55,20160127,1,'',168910,28.8500003814697,33.0,952.049987792969,3,10,377,0,0.0,5.20424461364746,1.71740078926086,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (56,20160130,1,'',169297,28.2999992370605,34.5900001525879,978.900024414063,3,10,387,0,0.0,7.31266212463379,2.52944993972778,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (57,20160203,1,'',169725,31.7600002288818,33.0,1048.07995605469,3,10,428,0,0.0,7.42056131362915,2.44878530502319,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (58,20160206,1,'',170178,28.4300003051758,34.5900001525879,983.390014648438,3,10,453,0,0.0,6.27593803405762,2.17084717750549,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (59,20160209,1,'',170581,29.1700000762939,33.0,962.609985351562,3,10,403,0,0.0,7.23821258544922,2.38861012458801,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (60,20160213,1,'',171010,28.6399993896484,33.6500015258789,963.739990234375,3,10,429,0,0.0,6.67599058151245,2.24647092819214,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (61,20160216,1,'',171474,33.2000007629395,33.1399993896484,1100.25,3,10,464,0,0.0,7.1551718711853,2.37122392654419,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (62,20160222,1,'',171919,10.0,32.4000015258789,324.0,3,8,445,0,0.0,8.9887638092041,2.91235947608948,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (63,20160222,1,'',172053,28.6100006103516,33.2000007629395,949.849975585938,3,10,134,0,0.0,0.0,-0.344387918710709,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (64,20160225,1,'',172522,33.2000007629395,33.2000007629395,1102.23999023438,3,10,469,0,0.0,7.07889080047607,2.35019159317017,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (65,20160228,1,'',172978,32.7599983215332,34.9900016784668,1146.27001953125,3,10,456,0,0.0,7.18420934677124,2.51375484466553,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (66,20160301,1,'',173445,32.3400001525879,33.2000007629395,1073.68994140625,3,10,467,0,0.0,6.92505264282227,2.29911756515503,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (67,20160303,1,'',173906,34.4700012207031,33.5499992370605,1156.46997070313,3,10,461,0,0.0,7.47722339630127,2.50860857963562,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (68,20160308,1,'',174358,31.9699993133545,34.9900016784668,1118.63000488281,3,10,452,0,0.0,7.07300901412964,2.47484588623047,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (69,20160311,1,'',174837,31.4200000762939,33.2000007629395,1043.14001464844,3,10,479,0,0.0,6.55949831008911,2.17775344848633,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (70,20160316,1,'',175276,34.7299995422363,33.6500015258789,1168.66003417969,3,10,439,0,0.0,7.91116046905518,2.66210579872131,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (71,20160320,1,'',175707,31.6399993896484,33.2000007629395,1050.44995117188,3,10,431,0,0.0,7.34106731414795,2.43723440170288,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (72,20160323,1,'',176096,30.2900009155273,33.2000007629395,1005.63000488281,3,10,389,0,0.0,7.7866325378418,2.58516216278076,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (73,20160327,1,'',176482,28.8099994659424,35.189998626709,1013.82000732422,3,10,386,0,0.0,7.46372985839844,2.6264865398407,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (74,20160409,1,'',176878,28.6299991607666,33.5,959.099975585938,3,10,396,0,0.0,7.22979736328125,2.42198204994202,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (75,20160416,1,'',177351,36.8400001525879,33.3899993896484,1230.08996582031,3,10,473,0,0.0,7.78858232498169,2.6006076335907,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (76,20160429,1,'',177803,25.7900009155273,34.5,889.760009765625,3,8,452,0,0.0,8.84955787658691,3.05309724807739,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (77,20160502,1,'',178276,14.4399995803833,32.4000015258789,468.0,3,8,473,0,0.0,5.45243120193481,1.7665878534317,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (78,20160508,1,'',178606,36.3600006103516,33.8899993896484,1232.23999023438,3,10,330,0,0.0,3.27272701263428,1.10912716388702,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (79,20160528,1,'',179100,14.8400001525879,33.7000007629395,500.0,3,10,494,0,0.0,3.00404858589172,1.01236438751221,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (80,20160529,1,'',179320,30.0400009155273,35.189998626709,1057.10998535156,3,10,220,0,0.0,13.6545457839966,4.80503416061401,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (81,20160531,1,'',179791,28.1100006103516,33.3899993896484,938.590026855469,3,10,471,0,0.0,5.96815299987793,1.99276626110077,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (82,20160611,1,'',180135,25.9099998474121,33.5900001525879,870.320007324219,3,10,344,0,0.0,7.53197813034058,2.52999138832092,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (83,20160618,1,'',180637,34.4300003051758,33.9000015258789,1167.18005371094,3,10,502,0,0.0,6.85856580734253,2.32505369186401,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (84,20160625,1,'',181109,26.3999996185303,34.2900009155273,905.260009765625,3,10,472,0,0.0,5.59322071075439,1.91791546344757,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (85,20160702,1,'',181548,27.4799995422363,35.7900009155273,983.510009765625,3,10,439,0,0.0,6.25967979431152,2.24033951759338,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (86,20160709,1,'',182009,30.25,34.4900016784668,1043.31994628906,3,10,461,0,0.0,6.56182193756104,2.26317238807678,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (87,20160715,1,'',182459,30.0599994659424,35.9900016784668,1081.85998535156,3,10,450,0,0.0,6.67999935150147,2.40413188934326,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (88,20160801,1,'',182955,13.8500003814697,36.0900001525879,499.850006103516,3,7,496,0,9.0,6.25,2.25562500953674,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (89,20160807,1,'',183200,14.9700002670288,33.4000015258789,500.0,3,8,245,0,0.0,9.32653045654297,3.11506152153015,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (90,20160811,1,'',183362,28.5499992370605,35.9900016784668,1027.51000976563,3,10,162,0,0.0,2.17283987998962,0.782005071640015,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (91,20160820,1,'',183819,14.5799999237061,34.2900009155273,499.950012207031,3,8,457,0,0.0,8.75273513793945,3.00131320953369,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (92,20160827,1,'',184049,30.7399997711182,35.9900016784668,1106.32995605469,3,10,230,0,0.0,2.31304359436035,0.832464337348938,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (93,20160911,1,'',184485,14.5299997329712,34.4000015258789,499.829986572266,3,8,436,0,0.0,9.17431163787842,3.15596342086792,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (94,20160921,1,'',184682,29.0200004577637,35.4900016784668,1029.92004394531,3,10,197,0,0.0,1.80203020572662,0.639540493488312,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (95,20161015,1,'',185068,28.7399997711182,35.4900016784668,1019.97998046875,3,10,386,0,0.0,7.44559526443481,2.64244174957275,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (96,20161104,1,'',185475,29.8999996185303,34.2900009155273,1025.27001953125,3,10,407,0,-1.0,7.34643793106079,2.51909375190735,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (97,20161203,1,'',185836,28.2800006866455,34.2900009155273,969.719970703125,3,10,361,0,-1.0,7.83379507064819,2.68620824813843,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (98,20161230,1,'',186072,22.5,34.8899993896484,785.02001953125,3,10,236,0,-1.0,9.53389835357666,3.32637691497803,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (99,20170122,1,'',186454,27.3299999237061,35.0,956.549987792969,3,10,382,0,-1.0,7.1544508934021,2.50405764579773,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (100,20170223,1,'',186810,30.9300003051758,36.3899993896484,1125.5400390625,3,10,356,0,-1.0,8.68820190429688,3.16163682937622,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (101,20170312,1,'',187233,31.3899993896484,36.3899993896484,1142.28002929688,3,10,423,0,-1.0,7.42080354690552,2.70043039321899,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (102,20150717,1,'',147969,34.439998626709,33.8499984741211,1165.7900390625,3,10,0,0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (103,20150721,1,'',148488,35.9199981689453,33.8499984741211,1215.89001464844,3,10,0,0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (104,20150726,1,'',149000,35.3899993896484,33.8499984741211,1197.94995117188,3,10,0,0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (105,20150730,1,'',149525,33.3300018310547,33.8499984741211,1128.21997070313,3,10,0,0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (106,20150802,1,'',149961,34.939998626709,32.9000015258789,1149.53002929688,3,10,0,0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (107,20150805,1,'',150417,29.2000007629395,33.8499984741211,988.419982910156,3,10,0,0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (108,20170319,1,'',187516,23.5900001525879,36.3899993896484,858.440002441406,3,10,283,0,-1.0,8.33568859100342,3.0333571434021,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (109,20170408,1,'',187962,31.6100006103516,35.2900009155273,1115.52001953125,3,10,446,0,-1.0,7.08744382858276,2.50115919113159,0.0,0.0,1.0,0.0,0.0,0);
INSERT INTO `rec_table` VALUES (110,20170423,1,'',188420,29.3199996948242,36.8899993896484,1081.60998535156,3,10,458,0,-1.0,6.40174674987793,2.3616042137146,0.0,0.0,1.0,0.0,0.0,0);
CREATE TABLE exp_table(_id integer primary key autoincrement, id_vehicle long, date integer default 0, mileage integer default 0, name string default '', note string default '',total_costwork float default 0, total_costpart float default 0 );
INSERT INTO `exp_table` VALUES (6,1,20150804,150200,'масло,колодки,подшипник','',3800.0,3800.0);
INSERT INTO `exp_table` VALUES (11,1,20150826,151780,'подшипник ступицы','',700.0,700.0);
INSERT INTO `exp_table` VALUES (19,1,20150925,155000,'','',550.0,550.0);
INSERT INTO `exp_table` VALUES (37,1,20151116,162781,'','',2977.0,2977.0);
INSERT INTO `exp_table` VALUES (54,1,20160112,167467,'ГБЦ и прокладки,сцепление','Прокладки,колпачки,трубки термостата',5116.0,5116.0);
INSERT INTO `exp_table` VALUES (55,1,20150526,140200,'Покрышки,тормозные диски','',6650.0,6650.0);
INSERT INTO `exp_table` VALUES (56,1,20150306,131400,'Ручки двери','',640.0,640.0);
INSERT INTO `exp_table` VALUES (57,1,20150214,128850,'Подшипник ступицы','',1700.0,1700.0);
INSERT INTO `exp_table` VALUES (58,1,20150208,127600,'','',5700.0,5700.0);
INSERT INTO `exp_table` VALUES (59,1,20150101,123176,'Масло','',1775.0,1775.0);
INSERT INTO `exp_table` VALUES (60,1,20141025,113000,'Кресло','',4600.0,4600.0);
INSERT INTO `exp_table` VALUES (61,1,20141023,112800,'Сцепление','Когда на трассе вырвало',5500.0,5500.0);
INSERT INTO `exp_table` VALUES (62,1,20150301,130000,'Электрозамок багажника','',450.0,450.0);
INSERT INTO `exp_table` VALUES (63,1,20141018,113300,'Шины','',5000.0,5000.0);
INSERT INTO `exp_table` VALUES (64,1,20141018,113310,'Течи масла','',0.0,0.0);
INSERT INTO `exp_table` VALUES (65,1,20140915,107450,'Ремень','',300.0,300.0);
INSERT INTO `exp_table` VALUES (66,1,20140705,99300,'Сцепление','',0.0,0.0);
INSERT INTO `exp_table` VALUES (67,1,20140702,98700,'Ходовая','',3970.0,3970.0);
INSERT INTO `exp_table` VALUES (68,1,20140611,98537,'Стеклоподъемник','',1305.0,1305.0);
INSERT INTO `exp_table` VALUES (69,1,20140611,98537,'Стекла боковые','Перед покупкой',1100.0,1100.0);
INSERT INTO `exp_table` VALUES (70,1,20140527,98347,'Шины','',4000.0,4000.0);
INSERT INTO `exp_table` VALUES (71,1,20140515,98347,'Аккумулятор,масло и др','',6000.0,6000.0);
INSERT INTO `exp_table` VALUES (72,1,20160110,166800,'Щетки','',500.0,500.0);
INSERT INTO `exp_table` VALUES (81,1,20160131,169300,'Регулировка клапанов и свеча','',2000.0,2000.0);
INSERT INTO `exp_table` VALUES (82,1,20160417,177432,'Задние колодки,переобувка','',0.0,0.0);
INSERT INTO `exp_table` VALUES (83,1,20160417,177432,'Задний тормозной цилиндр 3шт,жидкость','',1950.0,0.0);
INSERT INTO `exp_table` VALUES (84,1,20160501,0,'Тормозные трубки','',330.0,0.0);
INSERT INTO `exp_table` VALUES (85,1,20160528,0,'','',7350.0,0.0);
INSERT INTO `exp_table` VALUES (86,1,20160528,0,'Акб','',0.0,3390.0);
INSERT INTO `exp_table` VALUES (87,1,20160531,0,'Медкомисия','',2470.0,0.0);
INSERT INTO `exp_table` VALUES (88,1,20160813,183430,'Рхх','',0.0,600.0);
INSERT INTO `exp_table` VALUES (89,1,20160820,183450,'Глушитель+хомут+подвески','',0.0,1400.0);
INSERT INTO `exp_table` VALUES (90,1,20161023,0,'Замена стороны','',660.0,0.0);
INSERT INTO `exp_table` VALUES (91,1,20170308,0,'Кольцо приёмной трубы,термостат','',2000.0,680.0);
INSERT INTO `exp_table` VALUES (92,1,20170308,0,'Боковой прокол зад.прав.','',650.0,0.0);
INSERT INTO `exp_table` VALUES (93,1,20170312,187233,'Патрубки охл.,прокладка коллектора','',5700.0,450.0);
INSERT INTO `exp_table` VALUES (94,1,20170312,0,'Охлаждайка','',0.0,620.0);
INSERT INTO `exp_table` VALUES (95,1,20170312,0,'Прокладка коллектора','',0.0,120.0);