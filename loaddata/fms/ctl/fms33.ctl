LOAD DATA 
    CHARACTERSET ZHS16GBK 
    TRUNCATE INTO TABLE TIU_ACCOUNT_CHECK_TMP 
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
	SEQ_NO                         "trim(:SEQ_NO)",
	VOUCHER_BATCH_NAME             "trim(:VOUCHER_BATCH_NAME)",
	VOUCHER_NAME                   "trim(:VOUCHER_NAME)",
	CGL_TRADE_NO                   "trim(:CGL_TRADE_NO)",
	ACCOUNT_DATE                   "trim(:ACCOUNT_DATE)",
	CURRENCY                       "trim(:CURRENCY)",
	EXCHANGE_RATE                  "trim(:EXCHANGE_RATE)",
	IMAGE_ID                       "trim(:IMAGE_ID)",
	IMAGE_ADDR                     "trim(:IMAGE_ADDR)",
	VOUCHER_SEQ_NO                 "trim(:VOUCHER_SEQ_NO)",
	ORG_CODE                       "trim(:ORG_CODE)",
	DUTY_CODE                      "trim(:DUTY_CODE)",
	CGL_CODE                       "trim(:CGL_CODE)",
	REFERENCE                      "trim(:REFERENCE)",
	PRODUCT                        "trim(:PRODUCT)",
	COMPANY                        "trim(:COMPANY)",
	SPECIAL                        "trim(:SPECIAL)",
	RESERVE1                       "trim(:RESERVE1)",
	RESERVE2                       "trim(:RESERVE2)",
	DEBIT_AMT                      "trim(:DEBIT_AMT)",
	CREDIT_AMT                     "trim(:CREDIT_AMT)",
	ROW_MEMO                       "trim(:ROW_MEMO)",
	FILLER_23                      FILLER,
	FILLER_24                      FILLER,
	FILLER_25                      FILLER,
	FILLER_26                      FILLER,
	FILLER_27                      FILLER,
	FILLER_28                      FILLER,
	FILLER_29                      FILLER,
	FILLER_30                      FILLER,
	FILLER_31                      FILLER,
	FILLER_32                      FILLER,
	IMPORT_STATUS                  "trim(:IMPORT_STATUS)",
	ERROR_REASON                   "trim(:ERROR_REASON)"
)
