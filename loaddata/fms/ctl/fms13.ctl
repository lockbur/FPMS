LOAD DATA 
    CHARACTERSET ZHS16GBK 
    TRUNCATE INTO TABLE FMS_PROVIDER_ACT 
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
    FILLER_1               FILLER,
	PROVIDER_CODE          "trim(:PROVIDER_CODE)",
	PROVIDER_NAME          "trim(:PROVIDER_NAME)",
	PROVIDER_TYPE          "trim(:PROVIDER_TYPE)",
	PROVIDER_ADDR_CODE     "trim(:PROVIDER_ADDR_CODE)",
	PROVIDER_ADDR          "trim(:PROVIDER_ADDR)",
	PAY_CONDITION          "trim(:PAY_CONDITION)",
	PAY_MODE               "trim(:PAY_MODE)",
	BANK_NAME              "trim(:BANK_NAME)",
	BRANCH_NAME            "trim(:BRANCH_NAME)",
	ACT_NAME               "trim(:ACT_NAME)",
	ACT_NO                 "trim(:ACT_NO)",
	ACT_CURR               "trim(:ACT_CURR)",
	IS_MASTER_ACT          "trim(:IS_MASTER_ACT)",
	ACT_TYPE               "trim(:ACT_TYPE)",
	BANK_INFO              "trim(:BANK_INFO)",
	BANK_CODE              "trim(:BANK_CODE)",
	BANK_AREA              "trim(:BANK_AREA)",
	OU_ID                  "trim(:OU_ID)",
	OU_CODE                "trim(:OU_CODE)",
	OU_NAME                "trim(:OU_NAME)",
	FILLER_22               FILLER,
	FILLER_23               FILLER,
	FILLER_24               FILLER,
	FILLER_25               FILLER,
	FILLER_26               FILLER,
	FILLER_27               FILLER,
	FILLER_28               FILLER,
	FILLER_29               FILLER,
	FILLER_30               FILLER,
	FILLER_31               FILLER
)
