package cn.euraxluo.passbook.passbook.vo;

import cn.euraxluo.passbook.passbook.constant.FeedbackType;
import com.google.common.base.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * Feedback
 * 2019/12/29 12:46
 * author:Euraxluo
 * 用户评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    /** 用户 id */
    private Long userId;

    /** 评论类型 */
    private String type;

    /** PassTemplate RowKey, 如果是 app 类型的评论, 则没有 */
    private String templateId;

    /** 评论内容,可以控制coment的内容长度 */
    private String comment;

    /**
     * 如果this.type.toUpperCase()对应FeedbackType.class中的内容,
     * 则返回true,否则返回null
     * @return
     */
    public boolean validate(){
        FeedbackType feedbackType = Enums.getIfPresent(
                FeedbackType.class,this.type.toUpperCase()
        ).orNull();
        return !(null == feedbackType || null == comment);
    }
}
