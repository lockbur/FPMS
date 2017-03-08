LOAD DATA 
    CHARACTERSET ZHS16GBK 
    TRUNCATE INTO TABLE TID_ACCOUNT_TMP 
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
	SEQ_NO                      "trim(:SEQ_NO)",
	VOUCHER_BATCH_NAME          "trim(:VOUCHER_BATCH_NAME)",
	VOUCHER_NAME                "trim(:VOUCHER_NAME)",
	CREATE_DATE                 "trim(:CREATE_DATE)",
	GL_DATE                     "trim(:GL_DATE)",
	ACCOUNT_DATE                "trim(:ACCOUNT_DATE)",
	VOUCHER_NO                  "trim(:VOUCHER_NO)",
	VOUCHER_MEMO                "trim(:VOUCHER_MEMO)",
	CURRENCY                    "trim(:CURRENCY)",
	EXCHANGE_RATE               "trim(:EXCHANGE_RATE)",
	CGL_TRADE_NO                "trim(:CGL_TRADE_NO)",
	OU_CODE                     "trim(:OU_CODE)",
	ORG1_CODE                   "trim(:ORG1_CODE)",
	VOUCHER_SEQ_NO              "trim(:VOUCHER_SEQ_NO)",
	ORG_CODE                    "trim(:ORG_CODE)",
	DUTY_CODE                   "trim(:DUTY_CODE)",
	CGL_CODE                    "trim(:CGL_CODE)",
	REFERENCE                   "trim(:REFERENCE)",
	PRODUCT                     "trim(:PRODUCT)",
	COMPANY                     "trim(:COMPANY)",
	SPECIAL                     "trim(:SPECIAL)",
	RESERVE1                    "trim(:RESERVE1)",
	RESERVE2                    "trim(:RESERVE2)",
	DEBIT_AMT                   "nvl(trim(:DEBIT_AMT),0)",
	CREDIT_AMT                  "nvl(trim(:CREDIT_AMT),0)",
	ROW_MEMO                    "trim(:ROW_MEMO)",
	FILLER_27                   FILLER,
	FILLER_28                   FILLER,
	FILLER_29                   FILLER,
	FILLER_30                   FILLER,
	FILLER_31                   FILLER,
	FILLER_32                   FILLER,
	FILLER_33                   FILLER,
	FILLER_34                   FILLER,
	FILLER_35                   FILLER,
	FILLER_36                   FILLER
)
