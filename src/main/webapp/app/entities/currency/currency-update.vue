<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.currency.home.createOrEditLabel" v-text="$t('opusWebApp.currency.home.createOrEditLabel')">Create or edit a Currency</h2>
                <div>
                    <div class="form-group" v-if="currency.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="currency.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.currency.code')" for="currency-code">Code</label>
                        <input type="text" class="form-control" name="code" id="currency-code"
                            :class="{'valid': !$v.currency.code.$invalid, 'invalid': $v.currency.code.$invalid }" v-model="$v.currency.code.$model"  required/>
                        <div v-if="$v.currency.code.$anyDirty && $v.currency.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.currency.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.currency.code.pattern" v-text="$t('entity.validation.pattern', {pattern: 'Code'})">
                                This field should follow pattern for "Code".
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.currency.symbol')" for="currency-symbol">Symbol</label>
                        <input type="text" class="form-control" name="symbol" id="currency-symbol"
                            :class="{'valid': !$v.currency.symbol.$invalid, 'invalid': $v.currency.symbol.$invalid }" v-model="$v.currency.symbol.$model"  required/>
                        <div v-if="$v.currency.symbol.$anyDirty && $v.currency.symbol.$invalid">
                            <small class="form-text text-danger" v-if="!$v.currency.symbol.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.currency.name')" for="currency-name">Name</label>
                        <input type="text" class="form-control" name="name" id="currency-name"
                            :class="{'valid': !$v.currency.name.$invalid, 'invalid': $v.currency.name.$invalid }" v-model="$v.currency.name.$model"  required/>
                        <div v-if="$v.currency.name.$anyDirty && $v.currency.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.currency.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.currency.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./currency-update.component.ts">
</script>
