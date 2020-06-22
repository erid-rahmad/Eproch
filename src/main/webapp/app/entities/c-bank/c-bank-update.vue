<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.cBank.home.createOrEditLabel" v-text="$t('opusWebApp.cBank.home.createOrEditLabel')">Create or edit a CBank</h2>
                <div>
                    <div class="form-group" v-if="cBank.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cBank.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cBank.name')" for="c-bank-name">Name</label>
                        <input type="text" class="form-control" name="name" id="c-bank-name"
                            :class="{'valid': !$v.cBank.name.$invalid, 'invalid': $v.cBank.name.$invalid }" v-model="$v.cBank.name.$model"  required/>
                        <div v-if="$v.cBank.name.$anyDirty && $v.cBank.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cBank.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cBank.value')" for="c-bank-value">Value</label>
                        <input type="text" class="form-control" name="value" id="c-bank-value"
                            :class="{'valid': !$v.cBank.value.$invalid, 'invalid': $v.cBank.value.$invalid }" v-model="$v.cBank.value.$model"  required/>
                        <div v-if="$v.cBank.value.$anyDirty && $v.cBank.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cBank.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cBank.shortName')" for="c-bank-shortName">Short Name</label>
                        <input type="text" class="form-control" name="shortName" id="c-bank-shortName"
                            :class="{'valid': !$v.cBank.shortName.$invalid, 'invalid': $v.cBank.shortName.$invalid }" v-model="$v.cBank.shortName.$model"  required/>
                        <div v-if="$v.cBank.shortName.$anyDirty && $v.cBank.shortName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cBank.shortName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cBank.description')" for="c-bank-description">Description</label>
                        <input type="text" class="form-control" name="description" id="c-bank-description"
                            :class="{'valid': !$v.cBank.description.$invalid, 'invalid': $v.cBank.description.$invalid }" v-model="$v.cBank.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cBank.swiftCode')" for="c-bank-swiftCode">Swift Code</label>
                        <input type="text" class="form-control" name="swiftCode" id="c-bank-swiftCode"
                            :class="{'valid': !$v.cBank.swiftCode.$invalid, 'invalid': $v.cBank.swiftCode.$invalid }" v-model="$v.cBank.swiftCode.$model" />
                        <div v-if="$v.cBank.swiftCode.$anyDirty && $v.cBank.swiftCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cBank.swiftCode.pattern" v-text="$t('entity.validation.pattern', {pattern: 'Swift Code'})">
                                This field should follow pattern for "Swift Code".
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cBank.active')" for="c-bank-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="c-bank-active"
                            :class="{'valid': !$v.cBank.active.$invalid, 'invalid': $v.cBank.active.$invalid }" v-model="$v.cBank.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cBank.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./c-bank-update.component.ts">
</script>
