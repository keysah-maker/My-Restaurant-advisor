<?php

namespace App\Http\Controllers;

use App\Http\Resources\RestaurantResource;
use App\Models\Restaurant;
use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Resources\Json\AnonymousResourceCollection;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Validator;

class RestaurantController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return AnonymousResourceCollection ;
     */
    public function index()
    {
        return RestaurantResource::Collection(Restaurant::all());
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
     * @param Request $request
     * @return JsonResponse
     */
    public function store(Request $request)
    {

        $validator = Validator::make($request->all(), [
            'name' => 'required',
            'description' => 'required',
            'grade' => 'required',
            'localization' => 'required',
            'phone_number' => 'required',
            'website' => 'required',
            'hours' => 'required'
        ]);

        if ($validator->fails()) {
            return Response::json('Bad Request', 400);
        }

        $var = new Restaurant([
            'name' => $request->get('name'),
            'description' => $request->get('description'),
            'grade' => $request->get('grade'),
            'localization' => $request->get('localization'),
            'phone_number' => $request->get('phone_number'),
            'website' => $request->get('website'),
            'hours' => $request->get('hours')
        ]);

        $var->save();

        return Response::json('Created', 201);
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return void
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param int $id
     * @return Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return JsonResponse
     */
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'name' => 'required',
            'description' => 'required',
            'grade' => 'required',
            'localization' => 'required',
            'phone_number' => 'required',
            'website' => 'required',
            'hours' => 'required'
        ]);

        if ($validator->fails()) {
            return Response::json('Bad Request', 400);
        }

        Restaurant::where('id', $id)
            ->update([
                    'name' => $request->input('name'),
                    'description' => $request->input('description'),
                    'grade' => $request->input('grade'),
                    'localization' => $request->input('localization'),
                    'phone_number' => $request->input('phone_number'),
                    'website' => $request->input('website'),
                    'hours' => $request->input('hours')
                ]
            );

        return Response::json('OK', 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return JsonResponse
     */
    public function destroy($id)
    {
        $restaurant = Restaurant::find($id);

        if (!$restaurant)
            return Response::json('Bad Request', 400);

        if ($restaurant->delete())
            return Response::json('OK', 200);

        return Response::json('Bad Request', 400);
    }

}
