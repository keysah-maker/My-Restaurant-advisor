<?php

namespace App\Http\Controllers;

use App\Http\Resources\MenuResource;
use App\Models\Menu;
use App\Models\Restaurant;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Validator;

class MenuController extends Controller
{

    public function index(Restaurant $id)
    {
        return MenuResource::collection($id->menus()->get());
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return void
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Restaurant $id
     * @param Request $request
     * @return JsonResponse
     */
    public function store(Restaurant $id, Request $request)
    {
        $validator = Validator::make($request->all(), [
            'name' => 'required',
            'description' => 'required',
            'price' => 'required'
        ]);

        if ($validator->fails()) {
            return Response::json('Bad Request', 400);
        }

        $var = new Menu([
            'name' => $request->get('name'),
            'description' => $request->get('description'),
            'price' => $request->get('price'),
            'restaurant_id' => $id->getKey()
        ]);

        $var->save();

        return Response::json('Created', 201);
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param int $id
     * @return void
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Restaurant $restaurant
     * @param Request $request
     * @param Menu $id
     * @return JsonResponse
     */
    public function update($restaurant, Request $request, $id)
    {
        $find = Restaurant::find($restaurant);
        $menu = Menu::find($id);

        $validator = Validator::make($request->all(), [
            'name' => 'required',
            'description' => 'required',
            'price' => 'required'
        ]);

        if ($validator->fails() || empty($restaurant) || empty($menu) || $menu->restaurant_id != $find->id) {
            return Response::json('Bad Request', 400);
        }

        $menu->name = $request->get('name');
        $menu->description = $request->get('description');
        $menu->price = $request->get('price');

        $menu->save();

        return Response::json('OK', 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return JsonResponse
     */
    public function destroy($id, $menu_id)
    {
        $menu = Menu::where([['restaurant_id', $id], ['id', $menu_id]])->first();

        if (!$menu)
            return Response::json('Bad Request', 400);


        if ($menu->delete())
            return Response::json('OK', 200);

        return Response::json('Bad Request', 400);
    }
}
