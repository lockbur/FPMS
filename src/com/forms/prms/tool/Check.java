package com.forms.prms.tool;

public class Check {
	/***************************************************************************
	 * ���Ŀ������Ƿ�Ϊ��
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/***************************************************************************
	 * ���Ŀ������Ƿ�Ϊ��
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj, String objName) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/***************************************************************************
	 * ���Ŀ���ַ��Ƿ�Ϊ���ַ�
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) throws NullPointerException {
		// s������Ϊ��
		if (isNull(s)) {
			return true;
		}

		if (s.length() == 0) {
			return true;
		}
		return false;
	}

	/***************************************************************************
	 * ���Ŀ���ַ��Ƿ���ڸ���
	 * 
	 * @param s
	 * @return
	 */
	public static boolean checkLength(String s, int length)
			throws NullPointerException {
		// s������Ϊ��
		if (isNull(s)) {
			return false;
		}

		if (s.length() == length) {
			return true;
		} else {
			return false;
		}

	}

	/***************************************************************************
	 * �Ƚ�}�����
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
		// �ж��Ƿ�Ϊ��
		if (isNull(obj1) || isNull(obj2)) {
			return false;
		}

		// �Ƚϣ��ȽϹ����ɶ����Լ�ʵ��
		if (obj1.equals(obj2)) {
			return true;
		}

		return false;
	}

	/***************************************************************************
	 * �Ƚ�}�����,��������������Ϣ
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, String obj1Name, Object obj2,
			String obj2Name) {
		if (equals(obj1, obj2)) {
			return true;
		}

		return false;
	}

	/***************************************************************************
	 * �Ƚ�}������,��������������Ϣ
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(int obj1, String obj1Name, int obj2,
			String obj2Name) {
		if (obj1 == obj2) {
			return true;
		}

		return false;
	}

	/***************************************************************************
	 * �Ƿ�boolean��ֵ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBoolean(String s) {
		if (isNull(s)) {
			return false;
		}

		if (s.toLowerCase().equals("false") || s.toLowerCase().equals("true")) {
			return true;
		}
		return false;
	}

	/***************************************************************************
	 * �Ƿ�double��ֵ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDouble(String s) {
		if (isNull(s)) {
			return false;
		}

		if (s.indexOf(".") != s.lastIndexOf(".") || s.indexOf(".") == 0
				|| s.indexOf(".") == s.length() - 1) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != '.') {
				if (c > '9' || c < '0') {
					return false;
				}
			}
		}

		return true;
	}

	/***************************************************************************
	 * �Ƿ�����
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDigit(String s) {
		if (isNull(s) || isEmpty(s)) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > '9' || c < '0') {
				return false;
			}
		}

		return true;
	}

	/**
	 * �Ƿ��ǽ���ʽ
	 * 
	 * @param src
	 * @return
	 */
	public static boolean checkIsMoney(String src) {
		if (isNull(src) || isEmpty(src)) {
			return false;
		}
		try {
			Float f = new Float(src);
		} catch (NumberFormatException e) {
			return false;
		} catch (Exception ee) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * �Ƿ����ڸ�ʽ(yyyy-mm-dd)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDate(String s) {
		if (!checkLength(s, 10)) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (i == 4 || i == 7) {
				if (c != '-') {
					return false;
				}
			} else {
				if (c > '9' || c < '0') {
					return false;
				}
			}
		}

		return true;
	}

	/***************************************************************************
	 * �Ƿ�ʱ���ʽ(hh:mm:ss)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isTime(String s) {
		if (!checkLength(s, 8)) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (i == 2 || i == 5) {
				if (c != ':') {
					return false;
				}
			} else {
				if (c > '9' || c < '0') {
					return false;
				}
			}
		}

		return true;
	}

	// �ж��Ƿ�Ϊ�Ϸ���ȫ��
	public static boolean isSBC(String s) {
		int nLen = s.getBytes().length;

		// �ж��Ƿ���ż����ֽ�
		if ((nLen & 1) != 0) {
			return false;
		}

		for (int i = 0; i < nLen / 2; i++) {
			int lc = (byte) s.getBytes()[2 * i];
			lc = (lc >= 0 ? lc : 256 + lc);

			int rc = (byte) s.getBytes()[2 * i + 1];
			rc = (rc >= 0 ? rc : 256 + rc);

			if (lc <= 127 || rc <= 127) {
				return false;
			}
		}

		return true;
	}
}
