<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDTab.home.createOrEditLabel" v-text="$t('opusWebApp.aDTab.home.createOrEditLabel')">Create or edit a ADTab</h2>
                <div>
                    <div class="form-group" v-if="aDTab.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDTab.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.name')" for="ad-tab-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-tab-name"
                            :class="{'valid': !$v.aDTab.name.$invalid, 'invalid': $v.aDTab.name.$invalid }" v-model="$v.aDTab.name.$model"  required/>
                        <div v-if="$v.aDTab.name.$anyDirty && $v.aDTab.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDTab.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.description')" for="ad-tab-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-tab-description"
                            :class="{'valid': !$v.aDTab.description.$invalid, 'invalid': $v.aDTab.description.$invalid }" v-model="$v.aDTab.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.targetEndpoint')" for="ad-tab-targetEndpoint">Target Endpoint</label>
                        <input type="text" class="form-control" name="targetEndpoint" id="ad-tab-targetEndpoint"
                            :class="{'valid': !$v.aDTab.targetEndpoint.$invalid, 'invalid': $v.aDTab.targetEndpoint.$invalid }" v-model="$v.aDTab.targetEndpoint.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.level')" for="ad-tab-level">Level</label>
                        <input type="number" class="form-control" name="level" id="ad-tab-level"
                            :class="{'valid': !$v.aDTab.level.$invalid, 'invalid': $v.aDTab.level.$invalid }" v-model.number="$v.aDTab.level.$model" />
                        <div v-if="$v.aDTab.level.$anyDirty && $v.aDTab.level.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDTab.level.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.aDTab.level.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.writable')" for="ad-tab-writable">Writable</label>
                        <input type="checkbox" class="form-check" name="writable" id="ad-tab-writable"
                            :class="{'valid': !$v.aDTab.writable.$invalid, 'invalid': $v.aDTab.writable.$invalid }" v-model="$v.aDTab.writable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.displayLogic')" for="ad-tab-displayLogic">Display Logic</label>
                        <input type="text" class="form-control" name="displayLogic" id="ad-tab-displayLogic"
                            :class="{'valid': !$v.aDTab.displayLogic.$invalid, 'invalid': $v.aDTab.displayLogic.$invalid }" v-model="$v.aDTab.displayLogic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.readOnlyLogic')" for="ad-tab-readOnlyLogic">Read Only Logic</label>
                        <input type="text" class="form-control" name="readOnlyLogic" id="ad-tab-readOnlyLogic"
                            :class="{'valid': !$v.aDTab.readOnlyLogic.$invalid, 'invalid': $v.aDTab.readOnlyLogic.$invalid }" v-model="$v.aDTab.readOnlyLogic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.filterQuery')" for="ad-tab-filterQuery">Filter Query</label>
                        <input type="text" class="form-control" name="filterQuery" id="ad-tab-filterQuery"
                            :class="{'valid': !$v.aDTab.filterQuery.$invalid, 'invalid': $v.aDTab.filterQuery.$invalid }" v-model="$v.aDTab.filterQuery.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.orderQuery')" for="ad-tab-orderQuery">Order Query</label>
                        <input type="text" class="form-control" name="orderQuery" id="ad-tab-orderQuery"
                            :class="{'valid': !$v.aDTab.orderQuery.$invalid, 'invalid': $v.aDTab.orderQuery.$invalid }" v-model="$v.aDTab.orderQuery.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.active')" for="ad-tab-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-tab-active"
                            :class="{'valid': !$v.aDTab.active.$invalid, 'invalid': $v.aDTab.active.$invalid }" v-model="$v.aDTab.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.adClient')" for="ad-tab-adClient">Ad Client</label>
                        <select class="form-control" id="ad-tab-adClient" name="adClient" v-model="$v.aDTab.adClientId.$model" required>
                            <option v-if="!aDTab.adClientId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDClientOption.id" v-for="aDClientOption in aDClients" :key="aDClientOption.id">{{aDClientOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDTab.adClientId.$anyDirty && $v.aDTab.adClientId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDTab.adClientId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.adOrganization')" for="ad-tab-adOrganization">Ad Organization</label>
                        <select class="form-control" id="ad-tab-adOrganization" name="adOrganization" v-model="$v.aDTab.adOrganizationId.$model" required>
                            <option v-if="!aDTab.adOrganizationId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDOrganizationOption.id" v-for="aDOrganizationOption in aDOrganizations" :key="aDOrganizationOption.id">{{aDOrganizationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDTab.adOrganizationId.$anyDirty && $v.aDTab.adOrganizationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDTab.adOrganizationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.adTable')" for="ad-tab-adTable">Ad Table</label>
                        <select class="form-control" id="ad-tab-adTable" name="adTable" v-model="$v.aDTab.adTableId.$model" required>
                            <option v-if="!aDTab.adTableId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDTableOption.id" v-for="aDTableOption in aDTables" :key="aDTableOption.id">{{aDTableOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDTab.adTableId.$anyDirty && $v.aDTab.adTableId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDTab.adTableId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTab.adWindow')" for="ad-tab-adWindow">Ad Window</label>
                        <select class="form-control" id="ad-tab-adWindow" name="adWindow" v-model="$v.aDTab.adWindowId.$model" required>
                            <option v-if="!aDTab.adWindowId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDWindowOption.id" v-for="aDWindowOption in aDWindows" :key="aDWindowOption.id">{{aDWindowOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDTab.adWindowId.$anyDirty && $v.aDTab.adWindowId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDTab.adWindowId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDTab.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-tab-update.component.ts">
</script>
