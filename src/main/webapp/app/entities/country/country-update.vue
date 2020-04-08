<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.country.home.createOrEditLabel" v-text="$t('opusWebApp.country.home.createOrEditLabel')">Create or edit a Country</h2>
                <div>
                    <div class="form-group" v-if="country.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="country.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.country.name')" for="country-name">Name</label>
                        <input type="text" class="form-control" name="name" id="country-name"
                            :class="{'valid': !$v.country.name.$invalid, 'invalid': $v.country.name.$invalid }" v-model="$v.country.name.$model"  required/>
                        <div v-if="$v.country.name.$anyDirty && $v.country.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.country.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.country.code')" for="country-code">Code</label>
                        <input type="text" class="form-control" name="code" id="country-code"
                            :class="{'valid': !$v.country.code.$invalid, 'invalid': $v.country.code.$invalid }" v-model="$v.country.code.$model"  required/>
                        <div v-if="$v.country.code.$anyDirty && $v.country.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.country.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.country.code.pattern" v-text="$t('entity.validation.pattern', {pattern: 'Code'})">
                                This field should follow pattern for "Code".
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.country.currency')" for="country-currency">Currency</label>
                        <select class="form-control" id="country-currency" name="currency" v-model="country.currencyId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="currencyOption.id" v-for="currencyOption in currencies" :key="currencyOption.id">{{currencyOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.country.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./country-update.component.ts">
</script>
