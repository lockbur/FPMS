LOAD DATA 
    CHARACTERSET ZHS16GBK 
    TRUNCATE INTO TABLE FMS_USER
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
    FILLER_1             FILLER,
	USER_ID              "trim(:USER_ID)",
	USER_NAME            "trim(:USER_NAME)",
	ORG_CODE             "trim(:ORG_CODE)",
	ORG_NAME             "trim(:ORG_NAME)",
	DUTY_CODE            "trim(:DUTY_CODE)",
	DUTY_NAME            "trim(:DUTY_NAME)",
	OU_CODE              "trim(:OU_CODE)",
	OU_NAME              "trim(:OU_NAME)",
	STATUS               "trim(:STATUS)",
	LEAVE_DATE           "trim(:LEAVE_DATE)",
	IS_BUYER             "trim(:IS_BUYER)",
	IS_YG                "trim(:IS_YG)",
	FILLER_2             FILLER,
	FILLER_3             FILLER,
	FILLER_4             FILLER,
	FILLER_5             FILLER,
	FILLER_6             FILLER,
	FILLER_7             FILLER,
	FILLER_8             FILLER,
	FILLER_9             FILLER,
	FILLER_10            FILLER,
	FILLER_11            FILLER
)
