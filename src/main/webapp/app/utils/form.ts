import { ADColumnType } from '@/shared/model/ad-column.model';
import { IADField } from '@/shared/model/ad-field.model';
import { isStringField } from "@/utils/validate";

export const buildCascaderOptions = source => {
  let parents = new Map();
  let children = new Map();
  let result = [];

  const addToChildrenCache = item => {
    let items = children.get(item.parent);
    if (!items) {
      items = [];
      children.set(item.parent, items);
    }
    items.push(item);
  };

  const addChild = (parent, item) => {
    if (!parent.children) {
      parent.children = [];
    }
    parent.children.push(item);
  };

  const addChildren = item => {
    let items = children.get(item.value);
    if (items) {
      if (!item.children) {
        item.children = [];
      }
      item.children.concat(items);
    }
  };

  source.forEach(s => {
    let item = { ...s };
    if (item.parent) {
      let parent = parents.get(item.parent);
      if (!parent) {
        addToChildrenCache(item);
      } else {
        addChild(parent, item);
      }

      addChildren(item);
    } else {
      result.push(item);
      addChildren(item);
    }
    parents.set(item.value, item);
    delete item.parent;
  });

  return result;
};

export const normalizeField = (record: Record<string, any>, field: IADField) => {
  const fieldName = field.virtualColumnName || field.adColumn.name;
  const fieldType = field.type || field.adColumn.type;
  const isForeignKey = fieldName.endsWith('Id');

  if (! field.mandatory && ! field.adColumn?.mandatory) {
    const value = record[fieldName];
    const shouldNullify = (isForeignKey && !value) || (isStringField(field) && value?.trim() === '');
    if (shouldNullify) {
      record[fieldName] = null;
    }
  }

  if (fieldType === ADColumnType.LOCAL_DATE) {
    record[fieldName] = new Date(record[fieldName]);
  }
}
