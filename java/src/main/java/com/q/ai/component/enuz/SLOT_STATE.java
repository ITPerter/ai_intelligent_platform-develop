package com.q.ai.component.enuz;

/**
 * 没填入，填入未校验（一般不会输出到端），校验失败，校验选择中，校验成功
 */
public enum SLOT_STATE {
    UN_FILL, UN_VERIFY, VERIFY_FAIL, VERIFY_CHOOSE, VERIFY_SUCCESS
}
