<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="companyFunctionary">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.companyFunctionary.detail.title')">CompanyFunctionary</span> {{companyFunctionary.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.companyFunctionary.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{companyFunctionary.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.companyFunctionary.position')">Position</span>
                    </dt>
                    <dd>
                        <span>{{companyFunctionary.position}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.companyFunctionary.phone')">Phone</span>
                    </dt>
                    <dd>
                        <span>{{companyFunctionary.phone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.companyFunctionary.email')">Email</span>
                    </dt>
                    <dd>
                        <span>{{companyFunctionary.email}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.companyFunctionary.vendor')">Vendor</span>
                    </dt>
                    <dd>
                        <div v-if="companyFunctionary.vendorId">
                            <router-link :to="{name: 'VendorView', params: {vendorId: companyFunctionary.vendorId}}">{{companyFunctionary.vendorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="companyFunctionary.id" :to="{name: 'CompanyFunctionaryEdit', params: {companyFunctionaryId: companyFunctionary.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company-functionary-details.component.ts">
</script>
