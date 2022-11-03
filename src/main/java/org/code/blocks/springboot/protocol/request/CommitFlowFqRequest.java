package org.code.blocks.springboot.protocol.request;

import java.util.Date;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.code.blocks.springboot.protocol.validate.First;
import org.code.blocks.springboot.protocol.validate.Second;
import org.code.blocks.springboot.protocol.validate.Third;
import org.hibernate.validator.constraints.Length;


/**
 * @author darwindu
 * @date 2020/9/8
 **/
@Data
@GroupSequence({Third.class, First.class, Second.class, CommitFlowFqRequest.class})
public class CommitFlowFqRequest {


    @NotBlank(message = "房产证地址[详细地址]不能为空", groups = {First.class})
    @Length(min = 0, max = 50, message = "房产证地址[详细地址]长度限50位", groups = {First.class})
    private String houseCerAddress;

    @NotBlank(message = "业务流水号不能为空", groups = {Third.class, First.class, Second.class})
    private String flowId;
    @NotNull(message = "业务流水申请ID不能为空", groups = {Third.class, First.class, Second.class})
    private Long flowApplyId;

    @NotBlank(message = "姓名不能为空", groups = {First.class, Second.class})
    @Length(min = 0, max = 20, message = "姓名长度限20字符", groups = {First.class, Second.class})
    private String userName;

    @NotBlank(message = "身份证号不能为空", groups = {First.class, Second.class})
    @Length(min = 0, max = 18, message = "身份证号长度限18位", groups = {First.class, Second.class})
    @Pattern(regexp = "^\\d{17}[0-9a-zA-Z]|\\d{14}[0-9a-zA-Z]", message = "非法身份证", groups = {First.class, Second.class})
    private String certNum;

    @NotBlank(message = "借款额度不能为空", groups = {First.class})
    @Pattern(regexp = "[1-9]\\d{0,9}", message = "借款额度最大值为9999999999", groups = {First.class})
    private String quota;
    private Long quotaLong;

    @NotBlank(message = "抵押债券金额不能为空", groups = {First.class})
    @Pattern(regexp = "[1-9]\\d{0,9}", message = "抵押债券金额最大值为9999999999", groups = {First.class})
    private String dyQuota;
    private Long dyQuotaLong;

    @NotNull(message = "请选择婚姻状况", groups = {First.class})
    private Integer maritalStaus;

    @NotBlank(message = "邮箱不能为空", groups = {First.class, Second.class})
    @Length(min = 0, max = 32, message = "邮箱长度限32位", groups = {First.class, Second.class})
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "非法邮箱", groups = {
        First.class, Second.class})
    private String email;

    @NotBlank(message = "家庭地址[省]不能为空", groups = {First.class, Second.class})
    @Length(min = 0, max = 20, message = "家庭地址[省]长度限20位", groups = {First.class, Second.class})
    private String homeProvince;

    @NotBlank(message = "家庭地址[市]不能为空", groups = {First.class, Second.class})
    @Length(min = 0, max = 30, message = "家庭地址[市]长度限30位", groups = {First.class, Second.class})
    private String homeCity;

    @NotBlank(message = "家庭地址[区/县]不能为空", groups = {First.class, Second.class})
    @Length(min = 0, max = 30, message = "家庭地址[区/县]长度限30位", groups = {First.class, Second.class})
    private String homeCountry;

    @Length(min = 0, max = 30, message = "家庭地址[街道/镇]长度限30位", groups = {First.class, Second.class})
    private String homeTown;

    @NotBlank(message = "家庭地址[详细地址]不能为空", groups = {First.class, Second.class})
    @Length(min = 0, max = 50, message = "家庭地址[详细地址]长度限50位", groups = {First.class, Second.class})
    private String homeAddress;

    @Length(min = 0, max = 20, message = "公司地址[省]长度限20位", groups = {First.class})
    private String companyProvince;

    @Length(min = 0, max = 30, message = "公司地址[市]长度限30位", groups = {First.class})
    private String companyCity;

    @Length(min = 0, max = 30, message = "公司地址[区/县]长度限30位", groups = {First.class})
    private String companyCountry;

    @Length(min = 0, max = 30, message = "公司地址[街道/镇]长度限30位", groups = {First.class})
    private String companyTown;

    @Length(min = 0, max = 50, message = "公司地址[详细地址]长度限50位", groups = {First.class})
    private String companyAddress;

    @NotBlank(message = "房产编号不能为空", groups = {First.class})
    @Length(min = 0, max = 18, message = "房产编号长度限18位", groups = {First.class})
    private String houseCerNum;

    @NotBlank(message = "房产证地址[省]不能为空", groups = {First.class})
    @Length(min = 0, max = 20, message = "房产证地址[省]长度限20位", groups = {First.class})
    private String houseCerProvince;

    @NotBlank(message = "房产证地址[市]不能为空", groups = {First.class})
    @Length(min = 0, max = 30, message = "房产证地址[市]长度限30位", groups = {First.class})
    private String houseCerCity;

    @NotBlank(message = "房产证地址[区/县]不能为空", groups = {First.class})
    @Length(min = 0, max = 30, message = "房产证地址[区/县]长度限30位", groups = {First.class})
    private String houseCerCountry;

    @Length(min = 0, max = 30, message = "房产证地址[街道/镇]长度限30位", groups = {First.class})
    private String houseCerTown;

    private String borrower;
    private String borrowerCertNum;
    private Date lastApplyTime;
    private Integer applyStatus;
}