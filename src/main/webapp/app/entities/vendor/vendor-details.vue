<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="vendor">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.vendor.detail.title')">Vendor</span> {{vendor.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{vendor.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{vendor.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.npwp')">Npwp</span>
                    </dt>
                    <dd>
                        <span>{{vendor.npwp}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.branch')">Branch</span>
                    </dt>
                    <dd>
                        <span>{{vendor.branch}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.email')">Email</span>
                    </dt>
                    <dd>
                        <span>{{vendor.email}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.phone')">Phone</span>
                    </dt>
                    <dd>
                        <span>{{vendor.phone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.fax')">Fax</span>
                    </dt>
                    <dd>
                        <span>{{vendor.fax}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.website')">Website</span>
                    </dt>
                    <dd>
                        <span>{{vendor.website}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.type')">Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('opusWebApp.VendorType.' + vendor.type)">{{vendor.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.paymentCategory')">Payment Category</span>
                    </dt>
                    <dd>
                        <span v-text="$t('opusWebApp.PaymentCategory.' + vendor.paymentCategory)">{{vendor.paymentCategory}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.approvalStatus')">Approval Status</span>
                    </dt>
                    <dd>
                        <span v-text="$t('opusWebApp.VendorApprovalStatus.' + vendor.approvalStatus)">{{vendor.approvalStatus}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.vendor.businessCategory')">Business Category</span>
                    </dt>
                    <dd>
                        <span v-for="(businessCategory, i) in vendor.businessCategories" :key="businessCategory.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'BusinessCategoryView', params: {businessCategoryId: businessCategory.id}}">{{businessCategory.id}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="vendor.id" :to="{name: 'VendorEdit', params: {vendorId: vendor.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./vendor-details.component.ts">
</script>
