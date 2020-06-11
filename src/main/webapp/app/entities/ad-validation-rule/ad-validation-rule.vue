<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.adValidationRule.home.title')" id="ad-validation-rule-heading">Ad Validation Rules</span>
            <router-link :to="{name: 'AdValidationRuleCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-validation-rule">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.adValidationRule.home.createLabel')">
                    Create a new Ad Validation Rule
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && adValidationRules && adValidationRules.length === 0">
            <span v-text="$t('opusWebApp.adValidationRule.home.notFound')">No adValidationRules found</span>
        </div>
        <div class="table-responsive" v-if="adValidationRules && adValidationRules.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('uid')"><span v-text="$t('opusWebApp.adValidationRule.uid')">Uid</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'uid'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.adValidationRule.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.adValidationRule.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('query')"><span v-text="$t('opusWebApp.adValidationRule.query')">Query</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'query'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adValidationRuleId')"><span v-text="$t('opusWebApp.adValidationRule.adValidationRule')">Ad Validation Rule</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adValidationRuleId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="adValidationRule in adValidationRules"
                    :key="adValidationRule.id">
                    <td>
                        <router-link :to="{name: 'AdValidationRuleView', params: {adValidationRuleId: adValidationRule.id}}">{{adValidationRule.id}}</router-link>
                    </td>
                    <td>{{adValidationRule.uid}}</td>
                    <td>{{adValidationRule.name}}</td>
                    <td>{{adValidationRule.description}}</td>
                    <td>{{adValidationRule.query}}</td>
                    <td>
                        <div v-if="adValidationRule.adValidationRuleId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: adValidationRule.adValidationRuleId}}">{{adValidationRule.adValidationRuleId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdValidationRuleView', params: {adValidationRuleId: adValidationRule.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdValidationRuleEdit', params: {adValidationRuleId: adValidationRule.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(adValidationRule)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="opusWebApp.adValidationRule.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-adValidationRule-heading" v-text="$t('opusWebApp.adValidationRule.delete.question', {'id': removeId})">Are you sure you want to delete this Ad Validation Rule?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-adValidationRule" v-text="$t('entity.action.delete')" v-on:click="removeAdValidationRule()">Delete</button>
            </div>
        </b-modal>
        <div v-show="adValidationRules && adValidationRules.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-validation-rule.component.ts">
</script>
