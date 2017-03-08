LOAD DATA 
    CHARACTERSET ZHS16GBK 
    APPEND INTO TABLE TL_USER_INFO_DETAIL
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
	BATCH_NO                   "trim(:BATCH_NO)",
    SEQ                        "trim(:SEQ)",
    FILLER_1                   FILLER,
    FILLER_2                   FILLER,
    FILLER_3                   FILLER,
    FILLER_4                   FILLER,
    FILLER_5                   FILLER,
    FILLER_6                   FILLER,
    FILLER_7                   FILLER,
    FILLER_8                   FILLER,
    user_id                	   "trim(:user_id)",
    FILLER_9                   FILLER,
    is_admin                   "trim(:is_admin)",
    role_name                  "trim(:role_name)",
    FILLER_10                   FILLER,
    FILLER_11                   FILLER
)
