<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.companyFunctionary.home.createOrEditLabel" v-text="$t('opusWebApp.companyFunctionary.home.createOrEditLabel')">Create or edit a CompanyFunctionary</h2>
                <div>
                    <div class="form-group" v-if="companyFunctionary.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="companyFunctionary.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.companyFunctionary.name')" for="company-functionary-name">Name</label>
                        <input type="text" class="form-control" name="name" id="company-functionary-name"
                            :class="{'valid': !$v.companyFunctionary.name.$invalid, 'invalid': $v.companyFunctionary.name.$invalid }" v-model="$v.companyFunctionary.name.$model"  required/>
                        <div v-if="$v.companyFunctionary.name.$anyDirty && $v.companyFunctionary.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.companyFunctionary.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.companyFunctionary.position')" for="company-functionary-position">Position</label>
                        <input type="text" class="form-control" name="position" id="company-functionary-position"
                            :class="{'valid': !$v.companyFunctionary.position.$invalid, 'invalid': $v.companyFunctionary.position.$invalid }" v-model="$v.companyFunctionary.position.$model"  required/>
                        <div v-if="$v.companyFunctionary.position.$anyDirty && $v.companyFunctionary.position.$invalid">
                            <small class="form-text text-danger" v-if="!$v.companyFunctionary.position.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.companyFunctionary.phone')" for="company-functionary-phone">Phone</label>
                        <input type="text" class="form-control" name="phone" id="company-functionary-phone"
                            :class="{'valid': !$v.companyFunctionary.phone.$invalid, 'invalid': $v.companyFunctionary.phone.$invalid }" v-model="$v.companyFunctionary.phone.$model"  required/>
                        <div v-if="$v.companyFunctionary.phone.$anyDirty && $v.companyFunctionary.phone.$invalid">
                            <small class="form-text text-danger" v-if="!$v.companyFunctionary.phone.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.companyFunctionary.email')" for="company-functionary-email">Email</label>
                        <input type="text" class="form-control" name="email" id="company-functionary-email"
                            :class="{'valid': !$v.companyFunctionary.email.$invalid, 'invalid': $v.companyFunctionary.email.$invalid }" v-model="$v.companyFunctionary.email.$model"  required/>
                        <div v-if="$v.companyFunctionary.email.$anyDirty && $v.companyFunctionary.email.$invalid">
                            <small class="form-text text-danger" v-if="!$v.companyFunctionary.email.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.companyFunctionary.vendor')" for="company-functionary-vendor">Vendor</label>
                        <select class="form-control" id="company-functionary-vendor" name="vendor" v-model="companyFunctionary.vendorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="vendorOption.id" v-for="vendorOption in vendors" :key="vendorOption.id">{{vendorOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.companyFunctionary.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./company-functionary-update.component.ts">
</script>
