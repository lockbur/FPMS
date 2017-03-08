LOAD DATA 
    CHARACTERSET ZHS16GBK 
    TRUNCATE INTO TABLE FMS_ORG
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
	SEQ                  "trim(:SEQ)",
	ORG1_CODE            "trim(:ORG1_CODE)",
	ORG1_NAME            "trim(:ORG1_NAME)",
	ORG2_CODE            "trim(:ORG2_CODE)",
	ORG2_NAME            "trim(:ORG2_NAME)",
	ORG_CODE             "trim(:ORG_CODE)",
	ORG_NAME             "trim(:ORG_NAME)",
	DUTY_CODE            "trim(:DUTY_CODE)",
	DUTY_NAME            "trim(:DUTY_NAME)",
	IS_VALID             "trim(:IS_VALID)",
	INVALID_DATE         "trim(:INVALID_DATE)",
	FMS_CREATE_DATE      "trim(:FMS_CREATE_DATE)",
	OU_ID                "trim(:OU_ID)",
	OU_CODE              "trim(:OU_CODE)",
	OU_NAME              "trim(:OU_NAME)",
	DEF_ORG_DUTY         "trim(:DEF_ORG_DUTY)",
	DEF_DELIVERY         "trim(:DEF_DELIVERY)",
	STACK_ORG_CODE       "trim(:STACK_ORG_CODE)",
	STACK_ORG_NAME       "trim(:STACK_ORG_NAME)",
	ICMS                 "trim(:ICMS)",
	FILLER_1             FILLER,
	FILLER_2             FILLER,
	FILLER_3             FILLER,
	FILLER_4             FILLER,
	FILLER_5             FILLER,
	FILLER_6             FILLER,
	FILLER_7             FILLER,
	FILLER_8             FILLER,
	FILLER_9             FILLER,
	FILLER_10            FILLER
)
