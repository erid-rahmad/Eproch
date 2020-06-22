<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.cCurrency.home.createOrEditLabel" v-text="$t('opusWebApp.cCurrency.home.createOrEditLabel')">Create or edit a CCurrency</h2>
                <div>
                    <div class="form-group" v-if="cCurrency.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cCurrency.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCurrency.code')" for="c-currency-code">Code</label>
                        <input type="text" class="form-control" name="code" id="c-currency-code"
                            :class="{'valid': !$v.cCurrency.code.$invalid, 'invalid': $v.cCurrency.code.$invalid }" v-model="$v.cCurrency.code.$model"  required/>
                        <div v-if="$v.cCurrency.code.$anyDirty && $v.cCurrency.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cCurrency.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.cCurrency.code.pattern" v-text="$t('entity.validation.pattern', {pattern: 'Code'})">
                                This field should follow pattern for "Code".
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCurrency.symbol')" for="c-currency-symbol">Symbol</label>
                        <input type="text" class="form-control" name="symbol" id="c-currency-symbol"
                            :class="{'valid': !$v.cCurrency.symbol.$invalid, 'invalid': $v.cCurrency.symbol.$invalid }" v-model="$v.cCurrency.symbol.$model"  required/>
                        <div v-if="$v.cCurrency.symbol.$anyDirty && $v.cCurrency.symbol.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cCurrency.symbol.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCurrency.name')" for="c-currency-name">Name</label>
                        <input type="text" class="form-control" name="name" id="c-currency-name"
                            :class="{'valid': !$v.cCurrency.name.$invalid, 'invalid': $v.cCurrency.name.$invalid }" v-model="$v.cCurrency.name.$model"  required/>
                        <div v-if="$v.cCurrency.name.$anyDirty && $v.cCurrency.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cCurrency.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCurrency.active')" for="c-currency-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="c-currency-active"
                            :class="{'valid': !$v.cCurrency.active.$invalid, 'invalid': $v.cCurrency.active.$invalid }" v-model="$v.cCurrency.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cCurrency.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./c-currency-update.component.ts">
</script>
