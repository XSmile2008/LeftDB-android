/*
 * Copyright 2017 Andrii Horishnii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.andreyrage.leftdb.entities;

import com.github.andreyrage.leftdb.annotation.ColumnAutoInc;
import com.github.andreyrage.leftdb.annotation.ColumnChild;

/**
 * Created by rage on 11/18/15.
 */
public class ParentOne {
    @ColumnAutoInc private long id;
    private String name;
    @ColumnChild(foreignKey = "parentId", parentKey = "id") private ChildOne child;

    public ParentOne() {
    }

    public ParentOne(String name, ChildOne child) {
        this.name = name;
        this.child = child;
    }

    public ParentOne(long id, String name, ChildOne child) {
        this.id = id;
        this.name = name;
        this.child = child;
    }

    @Override
    public String toString() {
        return "ParentOne{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentOne parentOne = (ParentOne) o;

        if (id != parentOne.id) return false;
        if (name != null ? !name.equals(parentOne.name) : parentOne.name != null) return false;
        return !(child != null ? !child.equals(parentOne.child) : parentOne.child != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (child != null ? child.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChildOne getChild() {
        return child;
    }

    public void setChild(ChildOne child) {
        this.child = child;
    }
}
