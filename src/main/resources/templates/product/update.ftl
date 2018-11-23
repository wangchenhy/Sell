<!DOCTYPE html>
<html lang="zh">
<#include "../common/header.ftl">

<body>
<div id="main-wrapper">
        <#include "../common/sidebar.ftl">

        <#include "../common/topbar.ftl">
    <div class="page-wrapper">

        <!-- 页面功能导航 -->
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-5 align-self-center">
                    <h4 class="page-title">商品管理</h4>
                    <div class="d-flex align-items-center"></div>
                </div>
                <div class="col-7 align-self-center">
                    <div class="d-flex no-block justify-content-end align-items-center">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item">首页</li>
                                <li class="breadcrumb-item active" aria-current="page">
                                    商品
                                </li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
        </div>

<!-- 页面主体内容 -->
<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">商品更新</h4>
                </div>
            </div>
                <hr class="m-t-0">
                <form  action="${basePath}/seller/product/update/${productInfo.productId}" method="post">
                    <div class="card-body">
                        <h4 class="card-title">productInfo</h4>
                            <div type="hidden" class="form-group row align-items-center m-b-0">
                                <#--<label  for="inputEmail3" class="col-3 text-right control-label col-form-label">商品ID</label>-->
                                <div class="col-9 border-left p-b-10 p-t-10">
                                    <input type="hidden" value="${productInfo.productId}" name="productId" class="form-control" id="inputEmail3" placeholder="商品ID">
                                </div>
                            </div>
                        <div class="form-group row align-items-center m-b-0">
                            <label for="inputEmail3" class="col-3 text-right control-label col-form-label">商品名称</label>
                            <div class="col-9 border-left p-b-10 p-t-10">
                                <input type="text" value="${productInfo.productName}" name="productName" class="form-control" id="inputEmail3" placeholder="商品名称">
                            </div>
                        </div>
                        <div class="form-group row align-items-center m-b-0">
                            <label for="inputEmail3" class="col-3 text-right control-label col-form-label">商品价格</label>
                            <div class="col-9 border-left p-b-10 p-t-10">
                                <input type="text" value="${productInfo.productPrice}" name="productPrice" class="form-control" id="inputEmail3" placeholder="商品价格">
                            </div>
                        </div>
                        <div class="form-group row align-items-center m-b-0">
                            <label for="inputEmail3" class="col-3 text-right control-label col-form-label">商品库存</label>
                            <div class="col-9 border-left p-b-10 p-t-10">
                                <input type="text" value="${productInfo.productStock}" name="productStock" class="form-control" id="inputEmail3" placeholder="商品库存">
                            </div>
                        </div>
                        <div class="form-group row align-items-center m-b-0">
                            <label for="inputEmail3" class="col-3 text-right control-label col-form-label">商品描述</label>
                            <div class="col-9 border-left p-b-10 p-t-10">
                                <input type="text" value="${productInfo.productDescription}" name="productDescription" class="form-control" id="inputEmail3" placeholder="商品描述">
                            </div>
                        </div>

                        <div class="form-group row align-items-center m-b-0">
                            <label for="inputEmail3" class="col-3 text-right control-label col-form-label">商品状态</label>
                            <div class="col-9 border-left p-b-10 p-t-10">
                                <div class="custom-control custom-radio">
                                    <input type="radio" value="0" <#if productInfo.productStatus==0>checked="checked"</#if> name="productStatus" required id="styled_radio1" class="custom-control-input">
                                    <label class="custom-control-label" for="styled_radio1">上架</label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input type="radio" value="1" <#if productInfo.productStatus==1>checked="checked"</#if> name="productStatus" id="styled_radio2" class="custom-control-input">
                                    <label class="custom-control-label" for="styled_radio2">下架</label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group row align-items-center m-b-0">
                            <label for="inputEmail3" class="col-3 text-right control-label col-form-label">商品类别</label>
                            <div class="col-9 border-left p-b-10 p-t-10">
                                <select class="form-control" name="categoryType">
                                    <#list productCategoryList as productCategory >
                                        <option value="${productCategory.getCategoryType()}" <#if productCategory.getCategoryType() == productInfo.getCategoryType()> selected </#if>>
                                            ${productCategory.getCategoryName()}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row align-items-center m-b-0">
                            <label for="inputEmail3" class="col-3 text-right control-label col-form-label">商品照片</label>
                            <div class="col-9 border-left p-b-10 p-t-10">
                                <input type="text" name="productIcon" value="${productInfo.productIcon}" class="form-control" id="inputEmail3" placeholder="商品照片地址">
                            </div>
                        </div>
                        <div class="card-body">
                        <div class="form-group m-b-0 text-right">
                            <button type="submit" class="btn btn-info waves-effect waves-light">保存</button>
                        </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </div>
</div>
<#include "../common/layout.ftl">

<#include "../common/js.ftl">
</body>

</html>